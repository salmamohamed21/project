/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proxy;

import core.DatabaseOperations;
import javax.swing.JOptionPane;

/**
 *
 * @author sh
 */
public class UserAccessProxy implements UserAccess {
    private UserAccess realAccess;

    @Override
    public void login(String username, String password) {
        try {
            DatabaseOperations dbOps = new DatabaseOperations();
            String role = dbOps.getUserRole(username, password); // Query to get user role

            if ("admin".equalsIgnoreCase(role)) {
                realAccess = new AdminAccess();
            } else if ("student".equalsIgnoreCase(role)) {
                realAccess = new StudentAccess();
            } else {
                throw new IllegalArgumentException("Invalid role or credentials.");
            }

            // Delegate the login to the real access object
            realAccess.login(username, password);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Login failed: " + e.getMessage());
        }
    }
}
