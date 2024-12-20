/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proxy;

import core.DatabaseOperations;
import gui.StudentGUI;
import java.util.List;
import javax.swing.JOptionPane;
import observers.GradeNotifier;
import observers.StudentNotifier;
import singleton.GradeProcessingSystem;

/**
 *
 * @author sh
 */
public class StudentAccess implements UserAccess {
   private final GradeNotifier gradeNotifier = new GradeNotifier();

    @Override
public void login(String username, String password) {
    try {
        DatabaseOperations dbOps = new DatabaseOperations();
        String studentName = dbOps.getStudentName(username);

        // Open the Student GUI
        StudentGUI studentGUI = new StudentGUI();
        studentGUI.setVisible(true);

        // Initialize the GradeProcessingSystem with the student's username
        GradeProcessingSystem gradeProcessingSystem = GradeProcessingSystem.getInstance(username);

        // Create and register a notifier for the logged-in student
        StudentNotifier studentNotifier = new StudentNotifier(studentGUI, username);
        studentNotifier.loadNotifications(); // Load existing notifications

    } catch (Exception e) {
        e.printStackTrace();
    }
}

}
