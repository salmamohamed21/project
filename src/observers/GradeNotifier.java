/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package observers;
import java.util.ArrayList;
import java.util.List;

public class GradeNotifier implements GradeSubject {
    private final List<GradeObserver> observers = new ArrayList<>();

    @Override
    public void registerObserver(GradeObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(GradeObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String studentName, String courseName, double grade) {
        for (GradeObserver observer : observers) {
            observer.update(studentName, courseName, grade);
        }
    }

    // Method to simulate adding grades and triggering notifications
    public void addGrade(String studentName, String courseName, double grade) {
        System.out.println("Grade added: " + studentName + " - " + courseName + ": " + grade);
        notifyObservers(studentName, courseName, grade);
    }
}
