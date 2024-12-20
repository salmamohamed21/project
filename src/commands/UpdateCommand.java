/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commands;
import core.DatabaseOperations;
public class UpdateCommand implements Command {
     private final String entityType;         // نوع الكيان (Student)
    private final String entityName;         // الاسم القديم للكيان (اسم الطالب)
    private final String newValue;           // القيمة الجديدة (اسم الطالب الجديد)
    private final DatabaseOperations dbOps; // كائن للتعامل مع قاعدة البيانات

    // Constructor
    public UpdateCommand(String entityType, String entityName, String newValue, DatabaseOperations dbOps) {
        this.entityType = entityType;
        this.entityName = entityName;
        this.newValue = newValue;
        this.dbOps = dbOps;
    }

    @Override
    public void execute() {
        try {
            switch (entityType) {
                case "Student":
                    dbOps.updateStudent(entityName, newValue); // تعديل اسم الطالب
                    System.out.println("Student \"" + entityName + "\" updated to \"" + newValue + "\" successfully!");
                    break;

                default:
                    throw new IllegalArgumentException("Invalid entity type: " + entityType);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error updating " + entityType + ": " + e.getMessage());
        }
    }}
