public class Course {
    private String courseId;
    private String courseName;
    private String instructor;
    private String day;
    private String time;

    public Course(String courseId, String courseName, String instructor, String day, String time) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.instructor = instructor;
        this.day = day;
        this.time = time;
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
        return courseId + " - " + courseName + " (" + instructor + "), " + day + " at " + time;
    }
}