package finalProject;

/**
 * Abstract base class representing a person in the system.
 * Both {@link Student} and {@link Admin} extend this class.
 * 
 * <p> This class provides shared fields such as name and ID,
 * along with getter/setter functionality and a generic
 * displayInfo() method for debugging or text-based UIs.</p>
 */
public abstract class Person {
	
	// Person's full name.
    protected String name;

    // Unique ID assigned to the person (Student or Admin).
    protected String id;

    /**
     * Constructs a Person with the given name and ID.
     * @param name person's name
     * @param id person's ID
     */
    public Person(String name, String id) {
        this.name = name;
        this.id = id;
    }

    // Default constructor for flexibility.
    public Person() {}

    // -------------- Getters & Setters --------------

    /** @return the name of the person */
    public String getName() {
        return name;
    }

    /** @return the unique ID of the person */
    public String getId() {
        return id;
    }

    /** @param name new name to assign */
    public void setName(String name) {
        this.name = name;
    }

    /** @param id new ID to assign */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Displays Person information in a simple console format.
     * Primarily used for debugging.
     */
    public void displayInfo() {
        System.out.println("Name: " + name + ", ID: " + id);
    }
}