package finalProject;

public class Student extends Person implements CourseInterface {
    private Schedule schedule = new Schedule();

    @Override
    public void addCourse(Course course) {
        schedule.addCourse(course);
    }

    @Override
    public void removeCourse(Course course) {
        schedule.removeCourse(course);
    }

    public void viewSchedule() {
        System.out.println("Schedule for " + name + ":");
        for (Course c : schedule.getCourses()) {
            System.out.println(c);
        }
    }
}