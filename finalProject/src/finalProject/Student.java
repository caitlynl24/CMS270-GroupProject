package finalProject;

public class Student extends Person implements CourseInterface {
    private Schedule schedule = new Schedule();

    @Override
    public void addCourse(Course course) {
        if(course.isFull()) {
            System.out.println("Cannot add course. It is full.");
            return;
        }
        if(!schedule.hasConflict(course)) {
            schedule.addCourse(course);
            course.enrollStudent(this.id);
            System.out.println("Course added successfully.");
        } else {
            System.out.println("Schedule conflict detected.");
        }
    }

    @Override
    public void removeCourse(Course course) {
        schedule.removeCourse(course);
        course.removeStudent(this.id);
        System.out.println("Course removed.");
    }

    public void viewSchedule() {
        System.out.println("Schedule for " + name + ":");
        for (Course c : schedule.getCourses()) {
            System.out.println(c);
        }
    }
}