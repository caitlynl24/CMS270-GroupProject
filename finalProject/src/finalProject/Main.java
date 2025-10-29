package finalProject;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SchedulerSystem system = new SchedulerSystem();
        system.loadSampleData();

        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Welcome to the Course Scheduler System ===");

        while(true) {
            System.out.println("\nSelect role:");
            System.out.println("1. Student");
            System.out.println("2. Admin"); 
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            String choice = scanner.nextLine();

            switch(choice) {
                case "1":
                    handleStudent(system, scanner);
                    break;
                case "2":
                    handleAdmin(system, scanner);
                    break;
                case "3":
                    System.out.print("Exiting the system.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private static void handleStudent(SchedulerSystem system, Scanner scanner) {
        System.out.println("Enter student ID: ");
        String id = scanner.nextLine();

        Student student = system.findStudent(id);
        if(student == null) {
            student = new Student();
            student.setId(id);
            System.out.println("Enter student name: ");
            student.setName(scanner.nextLine());
            system.addStudent(student);
            System.out.println("New student created.");
        }

        while(true) {
            System.out.println("\nStudent Menu:");
            System.out.println("1. View Available Courses");
            System.out.println("2. Add Course");
            System.out.println("3. Remove Course");
            System.out.println("4. View My Schedule");
            System.out.println("5. Back");
            System.out.print("Enter choice: ");
            String choice = scanner.nextLine();

            switch(choice) {
                case "1":
                    for(Course c : system.getCourses()) {
                        System.out.println(c);
                    }
                    break;
                case "2":
                    System.out.print("Enter Course ID to add: ");
                    String addId = scanner.nextLine();
                    Course courseToAdd = system.findCourse(addId);
                    if(courseToAdd != null) {
                        student.addCourse(courseToAdd);
                        System.out.println("Course added.");
                    } else {
                        System.out.println("Course not found.");
                    }
                    break;
                case "3":
                    System.out.print("Enter Course ID to remove: ");
                    String removeId = scanner.nextLine();
                    Course courseToRemove = system.findCourse(removeId);
                    if(courseToRemove != null) {
                        student.removeCourse(courseToRemove);
                        System.out.println("Course removed.");
                    } else {
                        System.out.println("Course not found.");
                    }
                    break;
                case "4":
                    student.viewSchedule();
                    break;
                case "5":
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void handleAdmin(SchedulerSystem system, Scanner scanner) {
        System.out.println("Enter admin ID: ");
        String id = scanner.nextLine();

        Admin admin = system.findAdmin(id);
        if(admin == null) {
            admin = new Admin();
            admin.setId(id);
            System.out.println("Enter admin name: ");
            admin.setName(scanner.nextLine());
            system.addAdmin(admin);
            System.out.println("New admin created.");
        }

        while(true) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. View All Courses");
            System.out.println("2. Add Course");
            System.out.println("3. Remove Course");
            System.out.println("4. Back");
            System.out.print("Enter choice: ");
            String choice = scanner.nextLine();

            switch(choice) {
                case "1":
                    for(Course c : system.getCourses()) {
                        System.out.println(c);
                    }
                    break;
                case "2":
                    System.out.println("Enter Course ID: ");
                    String idInput = scanner.nextLine();
                    System.out.println("Enter Course Name: ");
                    String nameInput = scanner.nextLine();
                    System.out.println("Enter Instructor: ");
                    String instructor = scanner.nextLine();
                    System.out.println("Enter Day: ");
                    String day = scanner.nextLine();
                    System.out.println("Enter Time (HH:MM): ");
                    String time = scanner.nextLine();

                    if(!InputValidator.validateCourseId(idInput)) {
                        System.out.println("Invalid Course ID format (use e.g., CMS101).");
                        break;
                    }
                    if(!InputValidator.validateTimeFormat(time)) {
                        System.out.println("Invalid time format (use HH:MM).");
                        break;
                    }

                    Course newCourse = new Course(idInput, nameInput, instructor, day, time);
                    system.addCourse(newCourse);
                    admin.addCourse(newCourse);
                    break;
                case "3":
                    System.out.print("Enter Course ID to remove: ");
                    String removeId = scanner.nextLine();
                    Course courseToRemove = system.findCourse(removeId);
                    if(courseToRemove != null) {
                        admin.removeCourse(courseToRemove);
                        system.removeCourse(courseToRemove);
                        System.out.println("Course removed.");
                    } else {
                        System.out.println("Course not found.");
                    }
                    break;
                case "4":
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}