package finalProject;

import javax.swing.*;
import java.awt.*;

/**
 * Controller class that connects the {@link SchedulerView} to the 
 * {@link SchedulerSystem}. It handles all button actions for both
 * students and admins and manages navigation between GUI panels.
 * 
 * <p>This class does NOT modify the underlying data structures directly
 * except through {@link SchedulerSystem}. It exists solely to handle GUI
 * events, validate input, and pass objects to the model.</p>
 */
public class SchedulerController {

	// Reference to the application GUI view.
	private SchedulerView view;

	// Reference to the system model containing all application data.
	private SchedulerSystem system;
	
	/**
	 * Constructs the controller, binds the view and model, and attaches
	 * all event listeners for GUI interaction.
	 * 
	 * @param v the main GUI view
	 * @param sys the data model
	 */
	public SchedulerController(SchedulerView v, SchedulerSystem sys) {
		this.view = v;
		this.system = sys;
		attachEvents();
	}

	/**
	 * Attaches all button listeners for login, student mode, and admin mode.
	 * Validates ID format (Sxxxxx for students, Axxxxx for admins) and
	 * ensures required fields are present.
	 */
	private void attachEvents() {

		// ----------------------------------------
		//           Student login button
		// ----------------------------------------
		view.getStudentButton().addActionListener(e -> {

			String id = view.getIdField().getText().trim();
			if(!id.startsWith("S")) {
				JOptionPane.showMessageDialog(view, 
					"Student ID must start with 'S'.");
				return;
			}

			String name = view.getNameField().getText().trim();
			if(id.length() != 6) {
				JOptionPane.showMessageDialog(view, 
					"Student ID must be exactly 6 characters.", 
					"Invalid ID", 
					JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if(name.isEmpty()) {
				JOptionPane.showMessageDialog(view, 
					"Name cannot be empty.", 
					"Invalid Name", 
					JOptionPane.ERROR_MESSAGE);
				return;
			}

			Student student = system.findStudent(id);
			if(student == null) {
				student = new Student(name, id);
				system.addStudent(student);
			}

			showStudentPanel(student);
		});

		// ----------------------------------------
		//           Admin login button
		// ----------------------------------------
		view.getAdminButton().addActionListener(e -> {

			String id = view.getIdField().getText().trim();
			if(!id.startsWith("A")) {
				JOptionPane.showMessageDialog(view, 
					"Admin ID must start with 'A'.");
				return;
			}

			String name = view.getNameField().getText().trim();
			if(id.isEmpty() || name.isEmpty()) {
				JOptionPane.showMessageDialog(view, 
					"Please enter ID and Name.");
				return;
			}

			Admin admin = system.findAdmin(id);
			if(admin == null) {
				admin = new Admin(name, id);
				system.addAdmin(admin);
			}

			showAdminPanel(admin);
		});
	}

	/**
	 * Builds and displays the student panel with course list, schedule
	 * viewing, add/remove actions, and a back button
	 * 
	 * @param student the student currently logged in
	 */
	private void showStudentPanel(Student student) {

		JPanel panel = new JPanel(new BorderLayout());
		JLabel header = new JLabel("Student: " + student.getName(), SwingConstants.CENTER);
		panel.add(header, BorderLayout.NORTH);

		CourseTableModel tableModel = new CourseTableModel(system.getCourses());
		JTable courseTable = new JTable(tableModel);
		courseTable.setAutoCreateRowSorter(true);

		panel.add(new JScrollPane(courseTable), BorderLayout.CENTER);

		JPanel buttons = new JPanel();
		JButton addBtn = new JButton("Add Course");
		JButton removeBtn = new JButton("Remove Course");
		JButton scheduleBtn = new JButton("View Schedule");
		JButton backBtn = new JButton("Back");

		buttons.add(addBtn);
		buttons.add(removeBtn);
		buttons.add(scheduleBtn);
		buttons.add(backBtn);
		panel.add(buttons, BorderLayout.SOUTH);

		view.getMainPanel().add(panel, "STUDENT");
		view.showPanel("STUDENT");

		// ---------------------------------------
		//              Add Course
		// ---------------------------------------
		addBtn.addActionListener(e -> {
			int selectedRow = courseTable.getSelectedRow();
			if (selectedRow != -1) {
				int modelRow = courseTable.convertRowIndexToModel(selectedRow);
				Course selectedCourse = tableModel.getCourseAt(modelRow);
				student.addCourse(selectedCourse);
				tableModel.fireTableDataChanged();
			} else {
				JOptionPane.showMessageDialog(view, 
					"Please select a course to add.");
			}
		});

		// ---------------------------------------
		//             Remove Course
		// ---------------------------------------
		removeBtn.addActionListener(e -> {
			int selectedRow = courseTable.getSelectedRow();
			if (selectedRow != -1) {
				int modelRow = courseTable.convertRowIndexToModel(selectedRow);
				Course selectedCourse = tableModel.getCourseAt(modelRow);
				student.removeCourse(selectedCourse);
				tableModel.fireTableDataChanged();
			} else {
				JOptionPane.showMessageDialog(view, 
					"Please select a course to remove.");
			}
		});

		// ---------------------------------------
		//             View Schedule
		// ---------------------------------------
		scheduleBtn.addActionListener(e -> {
			StringBuilder sb = new StringBuilder();
			for (Course c : student.getSchedule().getCourses()) {
				sb.append(c).append("\n");
			}
			JOptionPane.showMessageDialog(view, 
				sb.toString(), 
				"My Schedule", 
				JOptionPane.INFORMATION_MESSAGE);
		});

		// ---------------------------------------
		//               Back
		// ---------------------------------------
		backBtn.addActionListener(e -> view.showPanel("HOME"));
	}

	/**
	 * Builds and displays the admin panel with course list and admin
	 * actions including adding and removing courses.
	 * 
	 * @param admin the admin currently logged in
	 */
	private void showAdminPanel(Admin admin) {

		JPanel panel = new JPanel(new BorderLayout());
		JLabel header = new JLabel("Admin: " + admin.getName(), SwingConstants.CENTER);
		panel.add(header, BorderLayout.NORTH);

		CourseTableModel tableModel = new CourseTableModel(system.getCourses());
		JTable courseTable = new JTable(tableModel);
		courseTable.setAutoCreateRowSorter(true);

		panel.add(new JScrollPane(courseTable), BorderLayout.CENTER);

		JPanel buttons = new JPanel();
		JButton addBtn = new JButton("Add Course");
		JButton removeBtn = new JButton("Remove Course");
		JButton backBtn = new JButton("Back");

		buttons.add(addBtn);
		buttons.add(removeBtn);
		buttons.add(backBtn);
		panel.add(buttons, BorderLayout.SOUTH);

		view.getMainPanel().add(panel, "ADMIN");
		view.showPanel("ADMIN");

		// ---------------------------------------
		//             Add Course
		// ---------------------------------------
		addBtn.addActionListener(e -> showAddCourseDialog(admin, tableModel));
		
		// ---------------------------------------
		//           Remove Course
		// ---------------------------------------
		removeBtn.addActionListener(e -> {
			int selectedRow = courseTable.getSelectedRow();
			if (selectedRow != -1) {
				int modelRow = courseTable.convertRowIndexToModel(selectedRow);
				Course selectedCourse = tableModel.getCourseAt(modelRow);
				
				system.removeCourse(selectedCourse);
				admin.removeCourse(selectedCourse);
				tableModel.removeCourse(selectedCourse);

			} else {
				JOptionPane.showMessageDialog(view, "Please select a course to remove.");
			}
		});

		// ---------------------------------------
		//               Back
		// ---------------------------------------
		backBtn.addActionListener(e -> view.showPanel("HOME"));
	}

	/**
	 * Displays a dialog for administrators to manually create a new course.
	 * Ensures all fields are present, validates numeric values, checks for
	 * instructor schedule conflicts, and prevents duplicate IDs.
	 * 
	 * @param admin the admin creating the course
	 * @param tableModel table model to update upon successful course creation
	 */
	private void showAddCourseDialog(Admin admin, CourseTableModel tableModel) {
		
		JTextField idField = new JTextField();
		JTextField nameField = new JTextField();
		JTextField instrField = new JTextField();
		JTextField dayField = new JTextField();
		JTextField startField = new JTextField();
		JTextField endField = new JTextField();
		JTextField creditsField = new JTextField();
		JTextField capField = new JTextField();

		Object[] fields = {
			"Course ID:", idField,
			"Course Name:", nameField,
			"Instructor:", instrField,
			"Day:", dayField,
			"Start Time:", startField,
			"End Time:", endField,
			"Credits:", creditsField,
			"Capacity:", capField
		};

		int result = JOptionPane.showConfirmDialog(null, 
			fields, 
			"Add Course", 
			JOptionPane.OK_CANCEL_OPTION);

		if(result == JOptionPane.OK_OPTION) {

			// Validation: all fields filled
			if(idField.getText().trim().isEmpty() || 
				nameField.getText().trim().isEmpty() || 
				instrField.getText().trim().isEmpty() ||
			   	dayField.getText().trim().isEmpty() || 
				startField.getText().trim().isEmpty() || 
				endField.getText().trim().isEmpty() ||
			   	creditsField.getText().trim().isEmpty() || 
				capField.getText().trim().isEmpty()) {

				JOptionPane.showMessageDialog(null, 
					"All fields must be filled out.", 
					"Incomplete Form", 
					JOptionPane.ERROR_MESSAGE);
				return;
			}

			int credits, capacity;
			try {
				credits = Integer.parseInt(creditsField.getText().trim());
				capacity = Integer.parseInt(capField.getText().trim());
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, 
					"Credits and Capacity must be valid integers.", 
					"Invalid Input", 
					JOptionPane.ERROR_MESSAGE);
				return;
			}

			Course c = new Course(
				idField.getText(),
				nameField.getText(),
				instrField.getText(),
				dayField.getText(),
				startField.getText(),
				endField.getText(),
				credits,
				capacity
			);

			// Duplicate ID check
			if(system.findCourse(c.getCourseId()) != null) {
				JOptionPane.showMessageDialog(null, 
					"Course ID already exists.");
				return;
			}

			// Instructor conflict check
			for(Course existing : system.getCourses()) {

				if(!existing.getInstructor().equals(c.getInstructor())) continue;
				if(!existing.getDay().equals(c.getDay())) continue;
					
				boolean overlap = 
					!(existing.getEndTime().compareTo(c.getStartTime()) <= 0 || 
						existing.getStartTime().compareTo(c.getEndTime()) >= 0);
					
				if (overlap) {
					JOptionPane.showMessageDialog(null, 
						"Instructor has a scheduling conflict.", 
						"Conflict Error", 
						JOptionPane.ERROR_MESSAGE);
					return;
				}
			}

			admin.addCourse(c);
			system.addCourse(c);
			tableModel.addCourse(c);

			JOptionPane.showMessageDialog(null, 
				"Course added successfully.", 
				"Success", 
				JOptionPane.INFORMATION_MESSAGE);
		}
	}
}