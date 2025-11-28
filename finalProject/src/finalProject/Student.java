package finalProject;

public class Student extends Person implements CourseInterface {
    private Schedule schedule = new Schedule();

    public Schedule getSchedule() {
        return schedule;
    }

    public Student() {}
    public Student(String name, String id) {
        super(name, id);
    }

    @Override
    public void addCourse(Course course) {
        int totalCredits = schedule.getCourses().stream().mapToInt(Course::getCredits).sum();
        if(totalCredits + course.getCredits() > 16) {
            System.out.println("Cannot add course. Credit limit (16) exceeded.");
            return;
        }
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
        if(schedule.getCourses().contains(course)) {
            schedule.removeCourse(course);
            course.removeStudent(this.id);
            System.out.println("Course removed.");
        } else {
            System.out.println("Cannot remove course. It is not in your schedule.");
        }
    }

    public void viewSchedule() {
        System.out.println("Schedule for " + name + ":");
        for (Course c : schedule.getCourses()) {
            System.out.println(c);
        }
    }
}