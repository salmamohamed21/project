/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;
import java.sql.ResultSet;
public interface DatabaseAccess {
     // Method for executing SELECT queries
    ResultSet executeQuery(String query) throws Exception;

    // Method for executing INSERT, UPDATE, DELETE queries
    int executeUpdate(String query) throws Exception;
}
