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

        int courseCounter = 100;

        while (courses.size() < 100) {
            String prefix = prefixes[rand.nextInt(prefixes.length)];
            String courseId = prefix + courseCounter++;
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
                if(!dept.equals(prefix)) continue; //Skip course
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
            int capacity = 10 + rand.nextInt(16);

            courses.add(new Course(courseId, title, instructor, day, start, end, credits, capacity));
        }

        System.out.println("Loaded " + courses.size() + " courses successfully!");
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