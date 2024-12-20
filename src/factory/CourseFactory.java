/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factory;
import factory.models.CoreCourse;
import factory.models.Course;
import factory.models.ElectiveCourse;
import factory.models.LabCourse;
public class CourseFactory  {
      public static Course createCourse(String type, String name) {
         switch (type) {
            case "Core" : return new CoreCourse(name);
            case "Elective" : return new ElectiveCourse(name);
            case "Lab" : return new LabCourse(name);
            default : throw new IllegalArgumentException("Invalid course type: " + type);
        }
    }
}
