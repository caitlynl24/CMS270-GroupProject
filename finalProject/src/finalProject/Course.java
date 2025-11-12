package finalProject;

import java.util.HashSet;

public class Course {
	
	// Initialization of Course class variables
    private String courseId;
    private String courseName;
    private String instructor;
    private String day;
    private String startTime;
    private String endTime;
    private int credits;
    private int capacity;
    private HashSet<String> enrolledStudents = new HashSet<>();

    // Constructor function for course objects
    public Course(String courseId, String courseName, String instructor, String day, String startTime, String endTime, int credits, int capacity) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.instructor = instructor;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.credits = credits;
        this.capacity = capacity;
    }

    // Function checks if specific course is full and returns true or false accordingly
    public boolean isFull() {
        return enrolledStudents.size() >= capacity;
    }

    // Function returns the amount of open seats left in a course as an integer
    public int seatsLeft() {
        return capacity - enrolledStudents.size();
    }

    // Function checks if course is full. If not, adds the student using the student ID
    // If full, alerts the user the action was not completed and the course is full
    public boolean enrollStudent(String studentId) {
        if(isFull()) {
            System.out.println("Course " + courseId + " is full.");
            return false;
        }
        enrolledStudents.add(studentId);
        return true;
    }

    // Function removes a student from a course using the student ID
    public void removeStudent(String studentId) {
        enrolledStudents.remove(studentId);
    }

    // Uses a substring in the course ID to return the department of specified course
    public String getDepartment() {
        return courseId.substring(0, 3);
    }

    // Getter method for credits variable
    public int getCredits() {
        return credits;
    }

    // Getter method for Course ID variable
    public String getCourseId() {
        return courseId;
    }
    
    // Getter method for Course Name variable
    public String getCourseName() {
        return courseName;
    }
    
    // Getter method for Instructor variable
    public String getInstructor() {
        return instructor;
    }

    // Getter method for the day variable
    public String getDay() {
        return day;
    }

    // Getter method for the start time of a specified course
    public String getStartTime() {
        return startTime;
    }
    
    // Getter method for the end time of a specified course
    public String getEndTime() {
        return endTime;
    }

    // Setter method for Course ID variable
    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    // Setter method for Course name variable
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    // Setter method for instructor variable
    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    // Setter method for the day variable
    public void setDay(String day) {
        this.day = day;
    }

    // Setter method for the start time of a specified course
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    // Setter method for the end time of a specified course
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    // Converts course information to a user-friendly and readable format
    @Override
    public String toString() {
        return String.format("%s - %s (%s), %s %s-%s, %d credits, Seats left: %d", courseId, courseName, instructor, day, startTime, endTime, credits, seatsLeft());
    }
}