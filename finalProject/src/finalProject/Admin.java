package finalProject;

public class Admin extends Person implements CourseInterface {
	
	// Override the CourseInterface functions for specific admin use
    @Override
    public void addCourse(Course course) {
        System.out.println("Admin added course: " + course);
    }

    @Override
    public void removeCourse(Course course) {
        System.out.println("Admin removed course: " + course);
    }

    public void editCourse(Course course) {
        System.out.println("Admin edited course: " + course);
    }
}