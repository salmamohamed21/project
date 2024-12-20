/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commands;
import core.DatabaseOperations;
import factory.CourseFactory;
import factory.models.Course;
import factory.StudentFactory;
import factory.models.Student;
public class AddCommand implements Command {
    private final String entityType;         
    private final String entityName;
     private final String entityTypeSpecific;
    private final int userId;                
    private final DatabaseOperations dbOps; 

    // Constructor
    public AddCommand(String entityType, String entityName,String entityTypeSpecific,  int userId, DatabaseOperations dbOps) {
        this.entityType = entityType;
        this.entityName = entityName;
        this.entityTypeSpecific = entityTypeSpecific;
        this.userId = userId;
        this.dbOps = dbOps;
    }

    @Override
    public void execute() {
        try {
            switch (entityType) {
                case "Student":
                    Student student = StudentFactory.createStudent(entityTypeSpecific, entityName);
                    dbOps.addStudent(student, userId); // استدعاء addStudent مع كائن الطالب و userId
                    System.out.println("Student " + entityName + " added successfully!");
                    break;

                case "Course":
                    // استخدام CourseFactory لإنشاء الدورة
                    Course course = CourseFactory.createCourse(entityTypeSpecific, entityName);
                    dbOps.addCourse(course); // استدعاء addCourse مع كائن الدورة
                    System.out.println("Course " + entityName + " added successfully!");
                    break;

                default:
                    throw new IllegalArgumentException("Invalid entity type: " + entityType);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error adding " + entityType + ": " + e.getMessage());
        }
    }
}
