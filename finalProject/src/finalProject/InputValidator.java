public class InputValidator {
    public static boolean validateCourseId(String id) {
        return id != null && id.matches("[A-Z]{3}\\d{3}");
    }

    public static boolean validateTimeFormat(String time) {
        return time != null && time.matches("([01]?\\d|2[0-3]):[0-5]\\d");
    }

    public static boolean validateNonEmpty(String input) {
        return input != null && !input.trim().isEmpty();
    }
}