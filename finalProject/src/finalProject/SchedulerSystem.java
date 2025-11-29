package finalProject;

import java.util.*;

/**
 * Central model of the application that stores and manages all
 * {@link Course}, {@link Student}, and {@link Admin} records.
 * 
 * <p>This class acts as the main data layer for the MVC architecture.
 * It contains business logic for:
 * <ul>
 *      <li>Adding/removing/editing courses</li>
 *      <li>Registering/dropping students</li>
 *      <li>Searching courses, students, and admins by ID</li>
 *      <li>Loading sample courses with conflict and department validation</li>
 *      <li>Validating login for students and admins</li>
 * </ul>
 * 
 * <p>The system DOES NOT handle GUI operations; the controller and view
 * manage that layer. This class is strictly for data management.</p>
 */
public class SchedulerSystem {
    
    private static final int ID_LENGTH = 6;

    private final ArrayList<Student> students = new ArrayList<>();
    private final ArrayList<Admin> admins = new ArrayList<>();
    private final ArrayList<Course> courses = new ArrayList<>();
    private final HashMap<String, String> instructorDepartments = new HashMap<>();

    // ----------------------------------------
    //               Data Loading
    // ----------------------------------------

    /**
     * Generates and loads 100 random courses into the system with:
     * - department enforcement per instructor
     * - no schedule conflicts for instructors
     * - random days, times, credits, and capacities
     */
    public void loadSampleData() {
        Random rand = new Random();

        //Department prefixes and course names
        Map<String, String[]> deptTitles = new HashMap<>();
            deptTitles.put("CMS", new String[]{"Intro to Computer Science", "Prog & Software Development", "Computer Org & Architecture", "Data Structures and Algorithms", "Artificial Intelligence", "Database Design & Dev"});
            deptTitles.put("MAT", new String[]{"Statistical Reasoning", "Precalculus Mathematics", "Intro to Discrete Mathematics", "Linear Algebra", "Calculus I", "Calculus II", "Calculus III"});
            deptTitles.put("ENG", new String[]{"British Romance", "Shakespeare", "Oscar Wilde", "Asian American Stories", "Intro to Creative Writing", "Legal Writing", "Language Studies", "Writing for Publication"});
            deptTitles.put("HIS", new String[]{"China's Cultural Rev.", "History of a City: Ancient Athens", "Crusades", "Europe: Revolt, Nation, Empire", "History of a City: Cape Town"});
            deptTitles.put("BIO", new String[]{"Oceanography", "Microbiology", "Genetics", "Plant Physiology", "Molecular Biology", "General Biology II", "Human Physiology"});
            deptTitles.put("CHM", new String[]{"Chemistry I", "Chemistry II", "Organic Chemistry I", "Organic Chemistry II", "Physical Chemistry I"});
            deptTitles.put("BUS", new String[]{"Micro & Macro Economics", "Statistics for Business", "Global Entrepreneurship", "Financial Statement Analysis", "Investments", "Family Business Planning"});
            deptTitles.put("PSY", new String[]{"Intro to Psychology", "Perspectives in Psychology II", "Careers in Psychology", "Adolescent Development", "Sports Psych", "Organization Behavior", "Psychotherapy"});
            deptTitles.put("PHI", new String[]{"Ethics", "Intro to Philosophy", "Philosophy of Education", "Medical Ethics", "Feminist Theory", "Religion and Food", "Intro to Formal Logic"});
            deptTitles.put("ART", new String[]{"Two-Dimensional Foundations", "Three-Dimensional Foundations", "Sculpture II", "Painting II", "Graphic Design I", "Painting I"});
            deptTitles.put("COM", new String[]{"Intro Communication Studies", "Interpersonal Communication", "Mass Media & Society", "Public Speaking", "Persuasion", "Legal Communication", "Listening", "Health Communication"});
            deptTitles.put("MUS", new String[]{"Theory II", "Keyboard Harmony II", "Jazz Vision", "Song Writing I", "Recording Practicum", "Music Technology II", "Composition Practicum", "Conducting Practicum"});
            deptTitles.put("THE", new String[]{"Intro to Technical Theatre", "Acting I: Fundamentals", "Script Analysis", "History of Theatre II", "Mainstage Design/Tech", "Theatre Prod: Performance"});

        String[] prefixes = deptTitles.keySet().toArray(new String[0]);
        String[] instructors = {"D Roe", "A Hope", "A Fearing", "P Rogers", "V Alves", "T Loch", "M Fuse", "E Yook", "M Garcia", "R Winter", "D Myers", "V Summet", "M Rice", "Y Bing", "J Jones", "B Boles", "C Strom", "K Shi", "S Boyd", "M Anderson", "M Breckling", "S Kim", "M McLaren", "J Liu", "J Queen", "L Held", "T Staff", "M DiQuattro"};
        String[] days = {"MWF", "TR"};
        String[] times = {"08:00", "09:00", "10:00", "11:00", "12:00", "01:00", "02:00", "03:00", "04:00"};

        while (courses.size() < 100) {
            String prefix = prefixes[rand.nextInt(prefixes.length)];
            int num = 100 + rand.nextInt(900);
            String courseId = prefix + num;

            String[] titles = deptTitles.get(prefix);
            String title = titles[rand.nextInt(titles.length)];
            String instructor = instructors[rand.nextInt(instructors.length)];
            String day = days[rand.nextInt(days.length)];
            String start = times[rand.nextInt(times.length)];
            int startHour = Integer.parseInt(start.split(":")[0]);
            String end = String.format("%02d:00", (startHour % 12) + 1);

            //Enforce one department per instructor
            if(instructorDepartments.containsKey(instructor)) {
                if(!instructorDepartments.get(instructor).equals(prefix)) continue;
            } else {
                instructorDepartments.put(instructor, prefix);
            }

            //Prevent instructor from teaching overlapping courses
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
            int capacity = 10 + rand.nextInt(16);

            courses.add(new Course(courseId, title, instructor, day, start, end, credits, capacity));
        }
    }

    // ----------------------------------------
    //               Utilities
    // ----------------------------------------

    /**
     * Converts a 24-hour time string (HH:mm) to 12-hour format with AM/PM.
     * 
     * @param time 24-hour format time string
     * @return formatted 12-hour time string
     */
    public static String formatTime12(String time) {
        String[] parts = time.split(":");
        int hour = Integer.parseInt(parts[0]);
        String minute = parts[1];
        String suffix = (hour >= 12) ? "PM" : "AM";
        hour = hour % 12;
        if (hour == 0) hour = 12;
        return String.format("%02d:%s %s", hour, minute, suffix);
    }

    // ----------------------------------------
    //               Course Methods
    // ----------------------------------------

    // Returns a list of all courses.
    public ArrayList<Course> getCourses() {
        return courses;
    }

    // Finds a course by its ID.
    public Course findCourse(String id) {
        for (Course c : courses) {
            if(c.getCourseId().equalsIgnoreCase(id)) return c;
        }
        return null;
    }

    // Adds a course.
    public void addCourse(Course c) {
        courses.add(c);
    }

    //Removes a course.
    public void removeCourse(Course c) {
        courses.remove(c);
    }

    // ----------------------------------------
    //               Student Methods
    // ----------------------------------------

    // Finds a student by ID.
    public Student findStudent(String id) {
        for (Student s : students) {
            if (s.getId() != null && s.getId().equals(id)) return s;
        }
        return null;
    }

    // Adds a student with ID validation.
    public void addStudent(Student s) {
        try {
            if(s.getId().length() != ID_LENGTH) throw new IllegalArgumentException("Invalid ID length.");
            for(Student existing : students) {
                if(existing.getId().equals(s.getId())) throw new DuplicatedIdException("Duplicate student ID: " + s.getId());
            }
            students.add(s);
        } catch(DuplicatedIdException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            System.out.println("Student ID validation complete.");
        }
    }

    // ----------------------------------------
    //               Admin Methods
    // ----------------------------------------

    // Finds an admin by ID.
    public Admin findAdmin(String id) {
        for (Admin a : admins) {
            if (a.getId() != null && a.getId().equals(id)) return a;
        }
        return null;
    }

    // Adds an admin with ID validation.
    public void addAdmin(Admin a) {
        try {
            if(a.getId().length() != ID_LENGTH) throw new IllegalArgumentException("Invalid ID length.");
            for(Admin existing : admins) {
                if(existing.getId().equals(a.getId())) throw new DuplicatedIdException("Duplicate admin ID: " + a.getId());
            }
            admins.add(a);
        } catch(DuplicatedIdException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            System.out.println("Admin ID validation complete.");
        }
    }

    // ----------------------------------------
    //               Login Methods
    // ----------------------------------------

    /**
     * Validates a login ID and returns the corresponding Person.
     * Can be a Student or Admin.
     * 
     * @param id login ID
     * @return Person object if found, null otherwise
     */
    public Person validateLogin(String id) {
        Person p = findStudent(id);
        if (p == null) p = findAdmin(id);
        return p;
    }
}