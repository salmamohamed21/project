/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factory.models;

/**
 *
 * @author sh
 */
public class CourseGrade {
     private String courseName;
    private int grade;

    public CourseGrade(String courseName, int grade) {
        this.courseName = courseName;
        this.grade = grade;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getGrade() {
        return grade;
    }
}
