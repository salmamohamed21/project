package factory.models;

public class PartTimeStudent extends Student {
    public PartTimeStudent(String name) {
        super(name ,"Part-time");
    }

    @Override
    public void displayInfo() {
        System.out.println("Part-time Student: " + name);
    }
}
