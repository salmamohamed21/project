package singleton;

import factory.models.Course;
//import student.mangement.system.project.Subject;
import java.util.HashMap;
import java.util.Map;
public class CourseRegistrationSystem  {
     private static CourseRegistrationSystem instance;

    private final Map<String, String> enrollments;

    private CourseRegistrationSystem() {
        enrollments = new HashMap<>();
    }

    public static synchronized CourseRegistrationSystem getInstance() {
        if (instance == null) {
            instance = new CourseRegistrationSystem();
        }
        return instance;
    }

    public void enrollStudent(String courseName, String studentName) {
        enrollments.put(courseName, studentName);
        System.out.println(studentName + " has been enrolled in " + courseName);
    }

    public Map<String, String> getEnrollments() {
        return enrollments;
    }
}
