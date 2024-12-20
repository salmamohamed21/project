package core;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
     // Database URL, username, and password
    private static final String JDBC_URL = "jdbc:sqlserver://localhost:1433;encrypt=true;trustServerCertificate=true;databasename=StudentManagement; ";
   // private static final String JDBC_USERNAME = "root";
    //private static final String JDBC_PASSWORD = "password";

    // Singleton instance of Connection
    private static Connection connection;

    // Private constructor to prevent instantiation
    private DatabaseConnection() {}

    // Method to get the connection instance
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                synchronized (DatabaseConnection.class) {
                    if (connection == null || connection.isClosed()) {
                       
                            // Initialize connection
                            
                            connection = DriverManager.getConnection(JDBC_URL,"salma","123");
                            System.out.println("Database connected successfully!");
                        } 
                         }
                }
        }
           catch (SQLException e) {
                            e.printStackTrace();
                            System.out.println("Failed to connect to the database.");
                        }
      
        return connection;
    }

    // Close the connection (optional cleanup method)
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
