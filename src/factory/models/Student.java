package factory.models;

public abstract class Student {
    protected String name;
    private String type;
    public Student(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public abstract void displayInfo();

    public String getName() {
        return name;
    }
     public String getType() {
        return type;
    }
}
