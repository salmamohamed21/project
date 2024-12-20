/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;
import factory.CourseFactory;
import factory.models.Course;
import factory.models.CourseGrade;
import factory.models.Student;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DatabaseOperations implements DatabaseAccess{
    
    
    @Override
    public ResultSet executeQuery(String query) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            return stmt.executeQuery(query);
        } catch (Exception e) {
            throw new Exception("Error executing query: " + query, e);
        }
    }

    @Override
    public int executeUpdate(String query) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            return stmt.executeUpdate(query);
        } catch (Exception e) {
            throw new Exception("Error executing update: " + query, e);
        }
    }
    
    
     public String getUserRole(String username, String password) throws SQLException {
    String query = "SELECT user_type FROM Login WHERE username = ? AND password = ?";
    Connection conn = DatabaseConnection.getConnection();
    PreparedStatement stmt = conn.prepareStatement(query);
    stmt.setString(1, username);
    stmt.setString(2, password);

    ResultSet rs = stmt.executeQuery();
    if (rs.next()) {
        return rs.getString("user_type"); // Return the user type (e.g., "Admin" or "Student")
    } else {
        throw new SQLException("Invalid username or password.");
    }
}

    
    
    public int addLogin(String username, String password) throws SQLException {
        String query = "INSERT INTO Login (username, password, user_type) VALUES (?, ?, 'Student')";
        String selectQuery = "SELECT user_id FROM Login WHERE username = ?";
        int userId = -1;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement insertStmt = conn.prepareStatement(query);
             PreparedStatement selectStmt = conn.prepareStatement(selectQuery)) {

            // Insert login credentials
            insertStmt.setString(1, username);
            insertStmt.setString(2, password);
            insertStmt.executeUpdate();

            // Retrieve the generated user ID
            selectStmt.setString(1, username);
            ResultSet rs = selectStmt.executeQuery();
            if (rs.next()) {
                userId = rs.getInt("user_id");
            }
        }

        return userId;
    }
    
    public void addStudent(Student student, int userId) throws SQLException {
    String query = "INSERT INTO Students (name, user_id) VALUES (?, ?)";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

        stmt.setString(1, student.getName());
        stmt.setInt(2, userId);
        stmt.executeUpdate();
    }
}


    public void enrollStudentInCourse(String studentName, String courseName) throws SQLException {
      
           String query = "  INSERT INTO Enrollments (student_id, course_id) "+
           " SELECT s.student_id, c.course_id FROM Students s, Courses c "+
           " WHERE s.name = ? AND c.course_name = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, studentName);
            stmt.setString(2, courseName);
            stmt.executeUpdate();
        }
    }
      
    public List<String> getAllStudentNames() throws SQLException {
    List<String> studentNames = new ArrayList<>();
    String query = "SELECT name FROM Students";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            studentNames.add(rs.getString("name"));
        }
    }

    return studentNames;
}
    
    
    public ResultSet getStudentDetails(String studentName) throws SQLException {
     
           String query = " SELECT s.student_id, s.name, c.course_name, e.grade "+
           " FROM Students s JOIN Enrollments e ON s.student_id = e.student_id "+
           " JOIN Courses c ON e.course_id = c.course_id WHERE s.name = ? ";
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, studentName);
        return stmt.executeQuery();
    }

    public void updateStudent(String oldName, String newName) throws SQLException {
        String query = "UPDATE Students SET name = ? WHERE name = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, newName);
            stmt.setString(2, oldName);
            stmt.executeUpdate();
        }
    }

    public void deleteStudentAndEnrollments(String studentName) throws SQLException {
        String deleteEnrollmentsQuery = "DELETE FROM Enrollments "+
           " WHERE student_id = (SELECT student_id FROM Students WHERE name = ?)";

        String deleteStudentQuery = " DELETE FROM Students WHERE name = ? ";

        try (Connection conn = DatabaseConnection.getConnection()) {
            // Start transaction
            conn.setAutoCommit(false);

            // Delete enrollments
            try (PreparedStatement stmt = conn.prepareStatement(deleteEnrollmentsQuery)) {
                stmt.setString(1, studentName);
                stmt.executeUpdate();
            }

            // Delete student
            try (PreparedStatement stmt = conn.prepareStatement(deleteStudentQuery)) {
                stmt.setString(1, studentName);
                stmt.executeUpdate();
            }

            // Commit transaction
            conn.commit();
        } catch (Exception e) {
            throw new SQLException("Error deleting student and enrollments: " + e.getMessage(), e);
        }
    }
    
     // Method to delete a specific course for a student
    public void deleteStudentCourse(String studentName, String courseName) throws SQLException {
      
           String query = " DELETE FROM Enrollments "+
           " WHERE student_id = (SELECT student_id FROM Students WHERE name = ?)"+
             " AND course_id = (SELECT course_id FROM Courses WHERE course_name = ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, studentName);  // Set the student's name
            stmt.setString(2, courseName);  // Set the course name

            int rowsAffected = stmt.executeUpdate(); // Execute the query

            if (rowsAffected > 0) {
                System.out.println("Course \"" + courseName + "\" removed for student \"" + studentName + "\".");
            } else {
                System.out.println("No matching enrollment found for student \"" + studentName + "\" and course \"" + courseName + "\".");
            }
        }
    }
    
    // Method to view a student's grades
    public void viewStudentGrades(String studentName) {
       
           String query = " SELECT c.course_name, e.grade FROM Enrollments e "+
            " JOIN Students s ON e.student_id = s.student_id JOIN Courses c "
                   + "ON e.course_id = c.course_id WHERE s.name = ? ";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, studentName); // Set the student's name
            ResultSet rs = stmt.executeQuery();

            System.out.println("Grades for student: " + studentName);
            while (rs.next()) {
                String courseName = rs.getString("course_name");
                double grade = rs.getDouble("grade");
                System.out.println("Course: " + courseName + " | Grade: " + grade);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error retrieving grades for student: " + e.getMessage());
        }
    }
    
    
    public void addCourse(Course course) {
    String query = "INSERT INTO Courses (course_name, course_type) VALUES (?, ?)";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setString(1, course.getName()); // اسم الدورة
        stmt.setString(2, course.getType()); // نوع الدورة
        stmt.executeUpdate();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    public List<String> getAllCoursesinlist() throws SQLException {
    List<String> courses = new ArrayList<>();
    String query = "SELECT course_name FROM Courses";
    try (Connection conn = DatabaseConnection.getConnection();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(query)) {
        while (rs.next()) {
            courses.add(rs.getString("course_name"));
        }
    }
    return courses;
}


    public List<Course> getAllCourses() throws SQLException {
    List<Course> courses = new ArrayList<>();
    String query = "SELECT course_name, course_type FROM Courses";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            String courseName = rs.getString("course_name");
            String courseType = rs.getString("course_type");

            // Create a course object using the factory
            CourseFactory courseFactory = new CourseFactory();
            Course course = courseFactory.createCourse(courseType, courseName);

            courses.add(course);
        }
    }

    return courses;
}
    
    
    public void deleteCourse(String courseName) {
        String query = "DELETE FROM Courses WHERE course_name = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, courseName);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   public boolean updateCourse(String oldCourseName, String newCourseName, String courseType) throws SQLException {
    String query = "UPDATE Courses SET course_name = ?, course_type = ? WHERE course_name = ?";
    Connection conn = DatabaseConnection.getConnection();
    PreparedStatement stmt = conn.prepareStatement(query);
    stmt.setString(1, newCourseName);
    stmt.setString(2, courseType);
    stmt.setString(3, oldCourseName);

    int rowsAffected = stmt.executeUpdate();
    return rowsAffected > 0;
}

    
    
    public ResultSet getCourseDetails(String courseName) throws SQLException {
    String query = "SELECT course_name, course_type FROM Courses WHERE course_name = ?";
    Connection conn = DatabaseConnection.getConnection();
    PreparedStatement stmt = conn.prepareStatement(query);
    stmt.setString(1, courseName);
    return stmt.executeQuery();
}

    
    // إضافة درجة لطالب في دورة
    public void addGrade(String studentName, String courseName, double grade) {
        String query = "INSERT INTO Enrollments (student_id, course_id, grade) SELECT s.student_id, c.course_id,"+
                 " ? FROM Students s JOIN Courses c ON c.course_name = ? WHERE s.name = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDouble(1, grade);
            stmt.setString(2, courseName);
            stmt.setString(3, studentName);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // تحديث درجة لطالب في دورة
    public void updateGrade(String studentName, String courseName, double grade) {
       
          String query = "  UPDATE Enrollments SET grade = ?"+
           " WHERE student_id = (SELECT student_id FROM Students WHERE name = ?)"+
          "  AND course_id = (SELECT course_id FROM Courses WHERE course_name = ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDouble(1, grade);
            stmt.setString(2, studentName);
            stmt.setString(3, courseName);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
   
    
      public String getStudentName(String username) throws SQLException {
        String query = "SELECT name FROM Students WHERE student_id = (SELECT student_id FROM Login WHERE username = ?)";
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, username);

        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getString("name");
        } else {
            throw new SQLException("Student not found.");
        }
    }

    public List<String> getStudentNotifications(String username) throws Exception {
     
         String query = " SELECT notification FROM Notifications "+
           " WHERE student_id = (SELECT student_id FROM Login WHERE username = ?)";
      

        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, username);

        ResultSet rs = stmt.executeQuery();
        List<String> notifications = new ArrayList<>();
        while (rs.next()) {
            notifications.add(rs.getString("notification"));
        }
        return notifications;
    }

    public List<CourseGrade> getStudentCoursesAndGrades(String username) throws SQLException {
       
          String query = "  SELECT c.course_name, e.grade FROM Enrollments e "+
           " JOIN Courses c ON e.course_id = c.course_id "+
           " WHERE e.student_id = (SELECT student_id FROM Login WHERE username = ?)";
       
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, username);

        ResultSet rs = stmt.executeQuery();
        List<CourseGrade> courseGrades = new ArrayList<>();
        while (rs.next()) {
            courseGrades.add(new CourseGrade(rs.getString("course_name"), rs.getInt("grade")));
        }
        return courseGrades;
    }
}
