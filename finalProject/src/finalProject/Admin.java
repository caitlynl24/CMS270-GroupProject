package finalProject;

/**
 * Represents an administrative user has elevated privileges.
 * Admins can add, remove, and edit courses using logic different
 * from regular students.
 * 
 * <p>This class mainly acts as a semantic distinction, since most
 * course-modifying logic happens inside the Controller and
 * {@link SchedulerSystem}.</p>
 */
public class Admin extends Person implements CourseInterface {

    // Default constructor.
    public Admin() {}

    /**
    * Constructs an admin with a given name and ID.
    * 
    * @param name admin's name
    * @param id admin's ID
    */
    public Admin(String name, String id) {
        super(name, id);
    }
	
    @Override
    public void addCourse(Course course) {
        // Admin-side feedback
        System.out.println("Admin added course: " + course);
    }

    @Override
    public void removeCourse(Course course) {
        // Admin-side feedback
        System.out.println("Admin removed course: " + course);
    }

    /**
     * Allows editing of a course's details.
     * 
     * @param course the course being edited
     */
    public void editCourse(Course course) {
        System.out.println("Admin edited course: " + course);
    }
}