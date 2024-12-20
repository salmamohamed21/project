package singleton;

import core.DatabaseOperations;
import gui.StudentGUI;
import observers.GradeObserver;
import observers.StudentNotifier;

public class GradeProcessingSystem {
    private static GradeProcessingSystem instance;
    private final DatabaseOperations dbOps;
    private final GradeObserver notifier;

    private GradeProcessingSystem(String username) {
        dbOps = new DatabaseOperations(); // Initialize database operations
        // Create the GUI and notifier specific to the logged-in student
        StudentGUI studentGUI = new StudentGUI();
        notifier = new StudentNotifier(studentGUI, username); // Pass the username to the notifier
    }

    public static synchronized GradeProcessingSystem getInstance(String username) {
        if (instance == null) {
            instance = new GradeProcessingSystem(username);
        }
        return instance;
    }

    public static synchronized void resetInstance() {
        instance = null; // Reset the singleton instance (if needed)
    }

    public void addGrade(String studentName, String courseName, double grade) {
        dbOps.addGrade(studentName, courseName, grade); // Add grade to the database
        notifier.update(studentName, courseName, grade); // Notify the observer
    }

    public void updateGrade(String studentName, String courseName, double grade) {
        dbOps.updateGrade(studentName, courseName, grade); // Update grade in the database
        notifier.update(studentName, courseName, grade); // Notify the observer
    }
}
