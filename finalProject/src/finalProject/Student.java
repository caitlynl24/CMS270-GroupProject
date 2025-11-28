package finalProject;

import javax.swing.JOptionPane;

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
            JOptionPane.showMessageDialog(null, "Cannot add course. Credit limit (16) exceeded.", "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("Cannot add course. Credit limit (16) exceeded.");
            return;
        }
        if(course.isFull()) {
            JOptionPane.showMessageDialog(null, "Cannot add course. It is full.", "Course Full", JOptionPane.ERROR_MESSAGE);
            System.out.println("Cannot add course. It is full.");
            return;
        }
        if(!schedule.hasConflict(course)) {
            schedule.addCourse(course);
            course.enrollStudent(this.id);

            JOptionPane.showMessageDialog(null, "Course added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("Course added successfully.");
        } else {
            JOptionPane.showMessageDialog(null, "Schedule conflict detected.", "Conflict", JOptionPane.ERROR_MESSAGE);
            System.out.println("Schedule conflict detected.");
        }
    }

    @Override
    public void removeCourse(Course course) {
        if(schedule.getCourses().contains(course)) {
            schedule.removeCourse(course);
            course.removeStudent(this.id);

            JOptionPane.showMessageDialog(null, "Course removed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("Course removed.");
        } else {
            JOptionPane.showMessageDialog(null, "Cannot remove course. It is not in your schedule.", "Error", JOptionPane.ERROR_MESSAGE);
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