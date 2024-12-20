package factory.models;

public abstract class Course {
    String name;
    private String type;
    public Course(String name, String type) {
        this.name = name;
        this.type = type;
    }
     
    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}
