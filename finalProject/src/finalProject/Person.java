package finalProject;

public abstract class Person {
	
	// Initialize variables for person class
    protected String name;
    protected String id;

    // Getter method for name variable
    public String getName() {
        return name;
    }

    // Getter method for ID variable
    public String getId() {
        return id;
    }

    // Setter method for name variable
    public void setName(String name) {
        this.name = name;
    }

    // Setter method for ID variable
    public void setId(String id) {
        this.id = id;
    }

    // Displays information for a specific student in a user-friendly format
    public void displayInfo() {
        System.out.println("Name: " + name + ", ID: " + id);
    }
}