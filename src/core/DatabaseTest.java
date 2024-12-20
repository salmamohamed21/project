//package database;
//
//import java.sql.Connection;
//import java.sql.Statement;
//
//public class DatabaseTest {
//    public static void main(String[] args) {
//        try {
//            // Get the database connection
//            Connection connection = database.getConnection();
//
//            // Check if the connection is valid
//            if (connection != null) {
//                System.out.println("Database connection established successfully!");
//
//                // Optionally, perform a simple query to validate
//                Statement statement = connection.createStatement();
//                statement.execute("SELECT 1");
//                System.out.println("Test query executed successfully!");
//            } else {
//                System.out.println("Failed to establish database connection.");
//            }
//
//            // Close the connection (optional for a test)
//            database.closeConnection();
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("An error occurred while connecting to the database.");
//        }
//    }
//}






package core;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DatabaseTest {
    public static void main(String[] args) {
        try {
            // Get the database connection
            Connection connection = DatabaseConnection.getConnection();

            // Check if the connection is valid
            if (connection != null) {
                System.out.println("Database connection established successfully!");

                // Perform a simple query to validate
                Statement statement = connection.createStatement();
                statement.execute("SELECT 1");
                System.out.println("Test query executed successfully!");

                // Insert data into the Students table
                String insertSql = "INSERT INTO dbo.Students (Name, StudentType) VALUES (?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(insertSql)) {
                    preparedStatement.setString(1, "Test Student");
                    preparedStatement.setString(2, "Undergraduate");
                    int rowsAffected = preparedStatement.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Data inserted successfully!");

                        // Verify the insertion
                        String selectSql = "SELECT * FROM dbo.Students WHERE Name = 'Test Student'";
                        try (ResultSet resultSet = statement.executeQuery(selectSql)) {
                            if (resultSet.next()) {
                                System.out.println("Data verification successful: " +
                                        resultSet.getString("Name") + ", " +
                                        resultSet.getString("StudentType"));
                            } else {
                                System.out.println("Data verification failed.");
                            }
                        }
                    } else {
                        System.out.println("Data insertion failed.");
                    }
                }
            } else {
                System.out.println("Failed to establish database connection.");
            }

            // Close the connection (optional for a test)
            DatabaseConnection.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An error occurred while connecting to the database.");
        }
    }
}