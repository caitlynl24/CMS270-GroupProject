import java.util.ArrayList;

public class Schedule {
    private ArrayList<Course> courses = new ArrayList<>();

    public void addCourse(Course course) {
        if (!hasConflict(course)) {
            courses.add(course);
        } else {
            System.out.println("Conflict detected! Course not added: " + course.getCourseName());
        }
    }

    public void removeCourse(Course course) {
        courses.remove(course);
    }

    public boolean hasConflict(Course course) {
        for (Course c : courses) {
            if (c.getDay().equals(course.getDay()) && c.getTime().equals(course.getTime())) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }
}