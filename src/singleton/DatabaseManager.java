/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package singleton;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseManager {
     private static DatabaseManager instance;
    private Connection connection;

    private DatabaseManager() {
        try {
            String url = "jdbc:sqlserver://localhost:1433;databaseName=StudentManagement";
            String user = "sa";
            String password = "your_password";
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
