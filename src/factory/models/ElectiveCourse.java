package factory.models;

public class ElectiveCourse extends Course {
    
     public ElectiveCourse(String name) {
       super(name, "Elective");
    }

   // @Override
    public void displayInfo() {
        System.out.println("Elective Course: " + name);
    }
}



