/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factory;

import factory.models.Student;
import factory.models.GraduateStudent;
import factory.models.UndergraduateStudent;
import factory.models.PartTimeStudent;

public class StudentFactory {
    public static Student createStudent(String type, String name) {
         switch (type) {
            case "Undergraduate" : return new UndergraduateStudent(name);
            case "Graduate" : return new GraduateStudent(name);
            case "Part-time" : return new PartTimeStudent(name);
            default : throw new IllegalArgumentException("Invalid student type: " + type);
        }
    }
}
