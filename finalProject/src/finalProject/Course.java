package finalProject;

import java.util.HashSet;

public class Course {
    private String courseId;
    private String courseName;
    private String instructor;
    private String day;
    private String startTime;
    private String endTime;
    private int credits;
    private int capacity;
    private HashSet<String> enrolledStudents = new HashSet<>();

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

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
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

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return String.format("%s - %s (%s), %s %s-%s, %d credits, Seats left: %d", courseId, courseName, instructor, day, startTime, endTime, credits, seatsLeft());
    }
}