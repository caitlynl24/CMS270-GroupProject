public class Main {
    public static void main(String[] args) {
        SchedulerSystem system = new SchedulerSystem();
        system.loadSampleData();

        Student s1 = new Student();
        s1.setName("Alice");
        s1.setId("S123");

        Course c1 = system.getCourses().get(0);
        s1.addCourse(c1);
        s1.viewSchedule();

        InputValidator.validateCourseId("CMS101");
    }
}