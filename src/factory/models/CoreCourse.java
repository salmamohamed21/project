package factory.models;

public class CoreCourse extends Course {
 
     public CoreCourse(String name) {
         super(name, "Core");
    }

  //  @Override
    public void displayInfo() {
        System.out.println("Core Course: " + name);
    }
    
}
