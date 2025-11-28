package finalProject;

import java.util.*;

public class SchedulerSystem {
    private static final int ID_LENGTH = 6;

    private ArrayList<Student> students = new ArrayList<>();
    private ArrayList<Admin> admins = new ArrayList<>();
    private ArrayList<Course> courses = new ArrayList<>();
    private HashMap<String, String> instructorDepartments = new HashMap<>();

    public void loadSampleData() {
        Random rand = new Random();

        //Department prefixes and course names
        Map<String, String[]> deptTitles = Map.of(
            "CSE", new String[]{"Intro to Programming", "Data Structures", "Algorithms"},
            "MAT", new String[]{"Calculus I", "Linear Algebra", "Discrete Math"},
            "PHY", new String[]{"Physics I", "Thermodynamics", "Quantum Mechanics"},
            "ENG", new String[]{"English Composition", "World Literature", "Creative Writing"},
            "HIS", new String[]{"World History", "American History", "Modern Europe"},
            "BIO", new String[]{"Biology I", "Genetics", "Ecology"},
            "CHE", new String[]{"Chemistry I", "Organic Chemistry", "Physical Chemistry"},
            "ECO", new String[]{"Microeconomics", "Macroeconomics", "Econometrics"},
            "PSY", new String[]{"Intro to Psychology", "Cognitive Psychology", "Behavioral Science"},
            "CMS", new String[]{"Intro to Communication", "Public Speaking", "Media Studies"}
        );

        String[] prefixes = deptTitles.keySet().toArray(new String[0]);
        String[] instructors = {"Dr. Smith", "Dr. Jones", "Dr. Brown", "Dr. Taylor", "Dr. Wilson"};
        String[] days = {"M", "T", "W", "R", "F"};
        String[] times = {"08:00", "09:00", "10:00", "11:00", "12:00", "01:00", "02:00"};

        for (int i = 1; i <= 30; i++) {
            String prefix = prefixes[rand.nextInt(prefixes.length)];
            String courseId = prefix + (100 + i);
            String[] titles = deptTitles.get(prefix);
            String title = titles[rand.nextInt(titles.length)];
            String instructor = instructors[rand.nextInt(instructors.length)];
            String day = days[rand.nextInt(days.length)];

            String start = times[rand.nextInt(times.length)];
            int startHour = Integer.parseInt(start.split(":")[0]);
            String end = String.format("%02d:00", Math.min(startHour + 1, 23));

            //Enforce one department per instructor
            if(instructorDepartments.containsKey(instructor)) {
                String dept = instructorDepartments.get(instructor);
                if(!dept.equals(prefix)) continue; //skip course
            } else {
                instructorDepartments.put(instructor, prefix);
            }

            //Prevent instructor from teaching two courses at the same time
            boolean conflict = false;
            for(Course c : courses) {
                if(c.getInstructor().equals(instructor) &&
                c.getDay().equals(day)) {
                    boolean overlap = !(c.getEndTime().compareTo(start) <= 0 ||
                    c.getStartTime().compareTo(end) >= 0); 
                    if(overlap) {
                        conflict = true;
                        break;
                    }
                }
            }
            if(conflict) continue;

            int credits = 3 + rand.nextInt(2);
            int capacity = 5 + rand.nextInt(16);

            courses.add(new Course(courseId, title, instructor, day, start, end, credits, capacity));
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
        try {
            if(s.getId().length() != ID_LENGTH) {
                throw new IllegalArgumentException("Invalid ID length.");
            }
            for(Student existing : students) {
                if(existing.getId().equals(s.getId())) {
                    throw new DuplicatedIdException("Duplicate student ID: " + s.getId());
                }
            }
            students.add(s);
        } catch(DuplicatedIdException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            System.out.println("Student ID validation complete.");
        }
    }

    public void addAdmin(Admin a) {
        try {
            if(a.getId().length() != ID_LENGTH) {
                throw new IllegalArgumentException("Invalid ID length.");
            }
            for(Admin existing : admins) {
                if(existing.getId().equals(a.getId())) {
                    throw new DuplicatedIdException("Duplicate admin ID: " + a.getId());
                }
            }
            admins.add(a);
        } catch(DuplicatedIdException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            System.out.println("Admin ID validation complete.");
        }
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