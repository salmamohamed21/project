/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commands;

import core.DatabaseOperations;
public class DeleteCommand implements Command {
    private final String entityType;
    private final String entityName;
      private final String relatedEntityName; // اسم الكورس (في حال حذف كورس لطالب)
    private final DatabaseOperations dbOps;

    // Constructor
    public DeleteCommand(String entityType, String entityName, String relatedEntityName, DatabaseOperations dbOps) {
        this.entityType = entityType;
        this.entityName = entityName;
        this.relatedEntityName = relatedEntityName;
        this.dbOps = dbOps;
    }

    @Override
    public void execute() {
        try {
            switch (entityType) {
                case "Student":
                    dbOps.deleteStudentAndEnrollments(entityName); // حذف الطالب مع جميع تسجيلاته
                    System.out.println("Student \"" + entityName + "\" and all enrollments deleted successfully!");
                    break;

                case "Course":
                    dbOps.deleteStudentCourse(entityName, relatedEntityName); // حذف كورس محدد لطالب
                    System.out.println("Course \"" + relatedEntityName + "\" removed for student \"" + entityName + "\".");
                    break;

                default:
                    throw new IllegalArgumentException("Invalid entity type: " + entityType);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error deleting " + entityType + ": " + e.getMessage());
        }
    }
}
