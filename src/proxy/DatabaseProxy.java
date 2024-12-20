/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proxy;

import core.DatabaseOperations;
import factory.StudentFactory;
import factory.models.Student;
import java.sql.SQLException;
public class DatabaseProxy {
     private final DatabaseOperations dbOps;
    private final String userType;

    public DatabaseProxy(String userType) {
        this.dbOps = new DatabaseOperations();
        this.userType = userType;
    }

    private boolean isAdmin() {
        return "Admin".equalsIgnoreCase(userType);
    }

    public void addStudentWithLogin(String studentName, String studentType, String username, String password) throws SQLException {
        if (isAdmin()) {
            // إنشاء حساب تسجيل الدخول للطالب
            int userId = dbOps.addLogin(username, password);
            if (userId != -1) {
                // إنشاء كائن Student باستخدام StudentFactory
                Student student = StudentFactory.createStudent(studentType, studentName);
                // إضافة الطالب إلى قاعدة البيانات
                dbOps.addStudent(student, userId);
                System.out.println("Student " + studentName + " added successfully.");
            } else {
                System.out.println("Failed to create login for student.");
            }
        } else {
            System.out.println("Access Denied: Only admins can add students.");
        }
    }


    public void viewGrades(String studentName) {
        if ("Student".equalsIgnoreCase(userType)) {
            dbOps.viewStudentGrades(studentName);
        } else {
            System.out.println("Access Denied: Only students can view their grades.");
        }
    }
}
