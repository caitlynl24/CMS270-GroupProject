package finalProject;

import java.util.HashSet;

public class Course {
    private String courseId;
    private String courseName;
    private String instructor;
    private String day;
    private String time;
    private int credits;
    private int capacity;
    private HashSet<String> enrolledStudents = new HashSet<>();

    public Course(String courseId, String courseName, String instructor, String day, String time, int credits, int capacity) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.instructor = instructor;
        this.day = day;
        this.time = time;
        this.credits = credits;
        this.capacity = capacity;
    }

    public boolean isFull() {
        return enrolledStudents.size() >= capacity;
    }

    public int seatsLeft() {
        return capacity - enrolledStudents.size();
    }

    public boolean enrollStudent(String studentId) {
        if(isFull()) {
            System.out.println("Course " + courseId + " is full.");
            return false;
        }
        enrolledStudents.add(studentId);
        return true;
    }

    public void removeStudent(String studentId) {
        enrolledStudents.remove(studentId);
    }

    public String getDepartment() {
        return courseId.substring(0, 3);
    }

    public int getCredits() {
        return credits;
    }

        public String getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getInstructor() {
        return instructor;
    }

    public String getDay() {
        return day;
    }

    public String getTime() {
        return time;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return String.format("%s - %s (%s), %s at %s, %d credits, Seats left: %d", courseId, courseName, instructor, day, time, credits, seatsLeft());
    }
}