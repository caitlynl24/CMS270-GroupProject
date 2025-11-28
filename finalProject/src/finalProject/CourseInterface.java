package finalProject;

/**
 * Interface defining operations related to modifying courses.
 * 
 * <p> This allows both {@link Student} and {@link Admin} to implement
 * course-related behavior while maintaining different logic for
 * adding and removing courses.</p>
 */
public interface CourseInterface {
	
	/**
     * Adds a course to the implementing entity's managed list.
     * 
     * @param course the course to add
     */
    void addCourse(Course course);
    
    /**
     * Removes a course from the implementing entity's managed list.
     * 
     * @param course the course to remove
     */
    void removeCourse(Course course);
}