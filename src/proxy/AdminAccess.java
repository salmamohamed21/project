/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proxy;

import gui.AdminGUI;

/**
 *
 * @author sh
 */
public class AdminAccess implements UserAccess {
     @Override
    public void login(String username, String password) {
        // Logic to open Admin Form
        AdminGUI adminGUI = new AdminGUI();
        adminGUI.setVisible(true);
    }
}
