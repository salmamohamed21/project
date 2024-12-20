package factory.models;

public class GraduateStudent extends Student {
     public GraduateStudent(String name) {
        super(name , "Graduate");
    }

    @Override
    public void displayInfo() {
        System.out.println("Graduate Student: " + name);
    }
}
