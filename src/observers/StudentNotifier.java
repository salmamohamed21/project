/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package observers;

import core.DatabaseOperations;
import gui.StudentGUI;
import java.util.List;

public class StudentNotifier implements GradeObserver {
      private final StudentGUI studentGUI;
    private final String username;

    public StudentNotifier(StudentGUI studentGUI, String username) {
        this.studentGUI = studentGUI;
        this.username = username;
    }

    @Override
    public void update(String studentName, String courseName, double grade) {
        String notification = "Notification: " + studentName + " received a grade of " + grade + " in " + courseName;
        studentGUI.appendNotification(notification); // Add to JTextArea in StudentGUI
    }

    public void loadNotifications() {
        try {
            DatabaseOperations dbOps = new DatabaseOperations();
            List<String> notifications = dbOps.getStudentNotifications(username); // Fetch student-specific notifications
            for (String notification : notifications) {
                studentGUI.appendNotification(notification);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }}
