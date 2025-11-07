package finalProject;

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
            if (c.getDay().equals(course.getDay())) {
                if (timeOverlap(c.getStartTime(), c.getEndTime(), course.getStartTime(), course.getEndTime())) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean timeOverlap(String start1, String end1, String start2, String end2) {
        return start1.compareTo(end2) < 0 && start2.compareTo(end1) < 0;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }
}