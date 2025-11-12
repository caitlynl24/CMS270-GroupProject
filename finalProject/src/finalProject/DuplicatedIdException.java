package finalProject;

public class DuplicatedIdException extends Exception {
	
	// Custom duplicate ID error
    public DuplicatedIdException(String message) {
        super(message);
    }
}