package finalProject;

import javax.swing.JOptionPane;

/**
 * Represents a student in the system. Students maintain their own
 * {@link Schedule} and implement course addition/removal with
 * constraints such as:
 * 
 * <ul>
 *  <li>Maximum credit limit: 16</li>
 *  <li>Cannot add full courses</li>
 *  <li>Cannot add conflicting time slots</li>
 * </ul>
 */
public class Student extends Person implements CourseInterface {

    //Student's personal schedule containing their enrolled courses.
    private Schedule schedule = new Schedule();

    //Default constructor
    public Student() {}

    /**
     * Constructs a Student with the provided name and ID.
     * 
     * @param name student's name
     * @param id student's unique ID
     */
    public Student(String name, String id) {
        super(name, id);
    }

    /**
     * @return the student's schedule
     */
    public Schedule getSchedule() {
        return schedule;
    }

    // -------------------------------------------------------------
    //    CourseInterface Implementations
    // -------------------------------------------------------------

    @Override
    public void addCourse(Course course) {

        //Calculate total credits including the new course
        int currentCredits = schedule.getCourses()
                .stream()
                .mapToInt(Course::getCredits)
                .sum();

        // 1. Enforce credit limit
        if(currentCredits + course.getCredits() > 16) {
            JOptionPane.showMessageDialog(null,
                "Cannot add course. Credit limit (16) exceeded.",
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 2. Check for capacity
        if(course.isFull()) {
            JOptionPane.showMessageDialog(null, 
                "Cannot add course. It is full.", 
                "Course Full", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 3. Check scheduling conflicts
        if(!schedule.hasConflict(course)) {
            schedule.addCourse(course);
            course.enrollStudent(this.id);

            JOptionPane.showMessageDialog(null, 
                "Course added successfully.", 
                "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, 
                "Schedule conflict detected.", 
                "Conflict", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void removeCourse(Course course) {
        if(schedule.getCourses().contains(course)) {
            schedule.removeCourse(course);
            course.removeStudent(this.id);

            JOptionPane.showMessageDialog(null, 
                "Course removed successfully.", 
                "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, 
                "Cannot remove course. It is not in your schedule.", 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Prints the student's schedule to the console.
     * Mainly used for debugging.
     */
    public void viewSchedule() {
        System.out.println("Schedule for " + name + ":");
        for (Course c : schedule.getCourses()) {
            System.out.println(c);
        }
    }
}