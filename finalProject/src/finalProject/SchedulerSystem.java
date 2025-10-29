package finalProject;

import java.util.ArrayList;
import java.util.Random;

public class SchedulerSystem {
    private ArrayList<Student> students = new ArrayList<>();
    private ArrayList<Admin> admins = new ArrayList<>();
    private ArrayList<Course> courses = new ArrayList<>();

    public void loadSampleData() {
        admins.add(new Admin());
        students.add(new Student());

        Random rand = new Random();
        
        //50 unique courses
        String[] prefixes = {"CSE", "MAT", "PHY", "ENG", "HIS", "BIO", "CHE", "ECO", "PSY", "CMS"};
        String[] instructors = {"Dr. Smith", "Dr. Jones", "Dr. Brown", "Dr. Taylor", "Dr. Wilson"};
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        String[] times = {"8:00", "9:00", "10:00", "11:00", "12:00", "1:00", "2:00"};

        for (int i = 1; i <= 50; i++) {
            //Randomly pick a department prefix
            String prefix = prefixes[rand.nextInt(prefixes.length)];

            //Generate realistic course number (101, 201, 301, etc.)
            int courseLevel = (rand.nextInt(4) + 1) * 100 + (rand.nextInt(5) + 1);
            String courseId = String.format("%s%d", prefix, courseLevel);
            String courseName = "Course " + i;

            //Randomly assign instructor, day, and time
            String instructor = instructors[i % instructors.length];
            String day = days[i % days.length];
            String time = times[i % times.length];

            courses.add(new Course(courseId, courseName, instructor, day, time));
        }
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

    public Course findCourse(String id) {
        for (Course c : courses) {
            if(c.getCourseId().equalsIgnoreCase(id)) {
                return c;
            }
        }
        return null;
    }

    public void addStudent(Student s) {
        students.add(s);
    }

    public void addAdmin(Admin a) {
        admins.add(a);
    }

    public void addCourse(Course c) {
        courses.add(c);
    }

    public void removeCourse(Course c) {
        courses.remove(c);
    }

    public Person validateLogin(String id) {
        Person p = findStudent(id);
        if (p == null) {
            p = findAdmin(id);
        }
        return p;
    }
}