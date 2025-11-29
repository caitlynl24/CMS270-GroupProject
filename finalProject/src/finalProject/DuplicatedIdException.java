package finalProject;

/**
 * Exception thrown when attempting to create a Course or Student
 * with an ID already in use within the system.
 */
public class DuplicatedIdException extends Exception {
	
	/**
     * Creates an exception with the provided error message.
     * @param message msg explanation of the issue
     */
    public DuplicatedIdException(String msg) {
        super(msg);
    }
}