import java.util.ArrayList;

public class SchedulerSystem {
    private ArrayList<Student> students = new ArrayList<>();
    private ArrayList<Admin> admins = new ArrayList<>();
    private ArrayList<Course> courses = new ArrayList<>();

    public void loadSampleData() {
        admins.add(new Admin());
        students.add(new Student());
        courses.add(new Course("CMS101", "Intro to CMS", "Dr. Smith", "Monday", "10:00"));
        courses.add(new Course("MAT201", "Calculus II", "Dr. Jones", "Tuesday", "9:00"));
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public Student findStudent(String id) {
        for (Student s : students) {
            if (s.getId() != null && s.getId().equals(id)) {
                return s;
            }
        }
        return null;
    }

    public Admin findAdmin(String id) {
        for (Admin a : admins) {
            if (a.getId() != null && a.getId().equals(id)) {
                return a;
            }
        }
        return null;
    }

    public Person validateLogin(String id) {
        Person p = findStudent(id);
        if (p == null) {
            p = findAdmin(id);
        }
        return p;
    }
}