package factory.models;

public class UndergraduateStudent extends Student {
    public UndergraduateStudent(String name) {
        super(name , "Undergraduate");
    }

    @Override
    public void displayInfo() {
        System.out.println("Undergraduate Student: " + name);
    }
}
