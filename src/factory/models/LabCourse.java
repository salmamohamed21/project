package factory.models;

public class LabCourse extends Course {
    
     public LabCourse(String name) {
        super(name, "Lab");
    }

  //  @Override
    public void displayInfo() {
        System.out.println("Lab Course: " + name);
    }
}

