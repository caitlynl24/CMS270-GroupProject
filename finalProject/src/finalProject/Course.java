package finalProject;

import java.util.HashSet;

/**
 * Represents a university course with metadta such as course ID,
 * instructor, meeting days, times, credit value, and capacity.
 * 
 * <p>Each course keeps track of enrolled students by their IDs.
 * The class provides helper methods for time conflict checks, 
 * determining course fullness, and managing registration lists.</p>
 */
public class Course {
	
	// -------------- Core Course Fields --------------

    // Unique course ID following format (e.g., CMS230).
    private String courseId;

    // Human-readable title of the course.
    private String courseName;

    // Instructor assigned to the course.
    private String instructor;

    // Meeting days (e.g., "MWF", "TR").
    private String day;

    // Start time in 24-hour HH:mm format.
    private String startTime;

    // End time in 24-hour HH:mm format.
    private String endTime;

    // Number of academic credits assigned to this course.
    private int credits;

    // Maximum number of enrollments allowed.
    private int capacity;

    // Set of student IDs currently enrolled in this course.
    private HashSet<String> enrolledStudents = new HashSet<>();

    /**
     * Constructs a new Course with all required properties.
     * 
     * @param courseId unique identifier (AAA999)
     * @param courseName course title
     * @param instructor instructor name
     * @param day day pattern (MWF, TR)
     * @param startTime start time (24h format)
     * @param endTime end time (24h format)
     * @param credits academic credits
     * @param capacity maximum seats
     */
    public Course(String courseId, String courseName, String instructor, 
                String day, String startTime, String endTime, 
                int credits, int capacity) {

        this.courseId = courseId;
        this.courseName = courseName;
        this.instructor = instructor;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.credits = credits;
        this.capacity = capacity;
    }

    // -------------- Enrollment Logic --------------

    /**
     * @return true if the course is currently at capacity
     */
    public boolean isFull() {
        return enrolledStudents.size() >= capacity;
    }

    /**
     * @return number of available seats remaining
     */
    public int seatsLeft() {
        return capacity - enrolledStudents.size();
    }

    /**
     * Attempts to enroll a student by ID.
     * 
     * @param studentId ID of the student
     * @return true if added successfully, false if the course is full
     */
    public boolean enrollStudent(String studentId) {
        if(isFull()) {
            System.out.println("Course " + courseId + " is full.");
            return false;
        }
        enrolledStudents.add(studentId);
        return true;
    }

    /**
     * Removes a student from the course.
     * 
     * @param studentId student ID to remove
     */
    public void removeStudent(String studentId) {
        enrolledStudents.remove(studentId);
    }

    /**
     * Extracts department prefix from the course ID.
     * Example: CMS230 -> CMS
     * 
     * @return department prefix
     */
    public String getDepartment() {
        return courseId.substring(0, 3);
    }

    // -------------- Getters --------------

    public int getCredits() { return credits; }
    public int getCapacity() { return capacity; }
    public String getCourseId() { return courseId; }
    public String getCourseName() { return courseName; }
    public String getInstructor() { return instructor; }
    public String getDay() { return day; }
    public String getStartTime() { return startTime; }
    public String getEndTime() { return endTime; }

    // -------------- Setters --------------
    
    public void setCourseId(String courseId) { this.courseId = courseId; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    public void setInstructor(String instructor) { this.instructor = instructor; }
    public void setDay(String day) { this.day = day; }
    public void setStartTime(String startTime) { this.startTime = startTime; }
    public void setEndTime(String endTime) { this.endTime = endTime; }

    @Override
    public String toString() {
        return String.format(
            "%s - %s (%s), %s %s-%s, %d credits, Seats left: %d", 
            courseId, courseName, instructor, day, 
            startTime, endTime, credits, seatsLeft());
    }
}