package finalProject;

import java.util.ArrayList;

/**
 * Represents a student's personal schedule containing a list of
 * enrolled {@link Course} objects.
 * 
 * <p>This class performs conflict detection and enforces that a student
 * may not enroll in overlapping courses taught on the same day.</p>
 */
public class Schedule {

    // List of courses currently in the student's schedule
    private ArrayList<Course> courses = new ArrayList<>();

    /**
     * Attempts to add a course only if it does not conflict
     * with existing schedule entries.
     * 
     * @param course course to be added
     */
    public void addCourse(Course course) {
        if (!hasConflict(course)) {
            courses.add(course);
        } else {
            System.out.println("Conflict detected! Course not added: " + course.getCourseName());
        }
    }

    /**
     * Removes a course from the schedule.
     * 
     * @param course course to remove
     */
    public void removeCourse(Course course) {
        courses.remove(course);
    }

    /**
     * Determines whether the provided course overlaps with
     * any existing courses based on day and time.
     * 
     * @param course course to check for conflict
     * @return true if a conflict exists, false otherwise
     */
    public boolean hasConflict(Course course) {
        for (Course c : courses) {

            // Only compare times if the days match exactly
            if (c.getDay().equals(course.getDay())) {
                if (timeOverlap(c.getStartTime(), c.getEndTime(), 
                        course.getStartTime(), course.getEndTime())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Helper method determining whether two time ranges overlap.
     * 
     * @param start1 start of first course
     * @param end1 end of first course
     * @param start2 start of second course
     * @param end2 end of second course
     * @return true if time intervals intersect
     */
    private boolean timeOverlap(String start1, String end1, 
                                String start2, String end2) {

        // Overlap rule:
        // ranges (start1 < end2) AND (start2 < end1)
        return start1.compareTo(end2) < 0 && 
                start2.compareTo(end1) < 0;
    }

    /**
     * @return list of all courses in schedule
     */
    public ArrayList<Course> getCourses() {
        return courses;
    }
}