package finalProject;

/**
 * Utility class containing input validation methods used throughout
 * the controller and view layers.
 * 
 * <p>Supports validation for:
 * <ul>
 *      <li>Course ID format (e.g., CMS230)</li>
 *      <li>Time format (HH:mm, 24-hour)</li>
 *      <li>Non-empty string fields</li>
 * </ul>
 */
public class InputValidator {
	
	/**
     * Validates that a course ID is in the format:
     * THREE LETTERS + THREE DIGITS
     * 
     * @param id course ID string
     * @return true if valid
     */
    public static boolean isValidateCourseId(String id) {
        return id != null && id.matches("[A-Za-z]{3}\\d{3}");
    }

	/**
     * Validates time in 24-hour format HH:mm
     * 
     * @param time the time string
     * @return true if valid
     */
    public static boolean isValidateTime(String time) {
        return time != null && time.matches("([01]?\\d|2[0-3]):[0-5]\\d");
    }

	/**
     * Checks that the provided string is not null or empty.
     * 
     * @param value the input string
     * @return true if non-empty
     */
    public static boolean notEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }
}