/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package observers;

/**
 *
 * @author sh
 */
public interface GradeObserver {
     void update(String studentName, String courseName, double grade);
}
