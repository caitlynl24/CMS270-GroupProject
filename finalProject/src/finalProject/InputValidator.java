package finalProject;

public class InputValidator {
    public static boolean validateCourseId(String id) {
        return id != null && id.matches("[A-Z]{3}\\d{3}");
    }

    public static boolean validateTimeFormat(String time) {
        return time != null && time.matches("([01]?\\d|2[0-3]):[0-5]\\d");
    }

    public static boolean validateTimeRange(String start, String end) {
        if(!validateTimeFormat(start) || !validateTimeFormat(end)) return false;
        return start.compareTo(end) < 0; //End must be after start
    }

    public static boolean validateNonEmpty(String input) {
        return input != null && !input.trim().isEmpty();
    }
}