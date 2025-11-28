package finalProject;

import javax.swing.*;
import java.awt.*;

public class SchedulerController {
	private SchedulerView view;
	private SchedulerSystem system;
	
	public SchedulerController(SchedulerView v, SchedulerSystem sys) {
		this.view = v;
		this.system = sys;
		attachEvents();
	}

	private void attachEvents() {
		view.getStudentButton().addActionListener(e -> {
			String id = view.getIdField().getText().trim();
			String name = view.getNameField().getText().trim();

			if(id.length() != 6) {
				JOptionPane.showMessageDialog(view, "Student ID must be exactly 6 characters.", "Invalid ID", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if(name.isEmpty()) {
				JOptionPane.showMessageDialog(view, "Name cannot be empty.", "Invalid Name", JOptionPane.ERROR_MESSAGE);
				return;
			}

			Student student = system.findStudent(id);
			if(student == null) {
				student = new Student(name, id);
				system.addStudent(student);
			}

			showStudentPanel(student);
		});

		view.getAdminButton().addActionListener(e -> {
			String id = view.getIdField().getText().trim();
			String name = view.getNameField().getText().trim();
			
			if(id.isEmpty() || name.isEmpty()) {
				JOptionPane.showMessageDialog(view, "Please enter ID and Name.");
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

	private void showStudentPanel(Student student) {
		JPanel panel = new JPanel(new BorderLayout());
		JLabel header = new JLabel("Student: " + student.getName(), SwingConstants.CENTER);
		panel.add(header, BorderLayout.NORTH);

		CourseTableModel tableModel = new CourseTableModel(system.getCourses());
		JTable courseTable = new JTable(tableModel);
		courseTable.setAutoCreateRowSorter(true); //Enable sorting

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

		addBtn.addActionListener(e -> {
			int selectedRow = courseTable.getSelectedRow();
			if (selectedRow != -1) {
				int modelRow = courseTable.convertRowIndexToModel(selectedRow);
				Course selectedCourse = tableModel.getCourseAt(modelRow);
				student.addCourse(selectedCourse);
				tableModel.fireTableDataChanged();
			} else {
				JOptionPane.showMessageDialog(view, "Please select a course to add.");
			}
		});

		removeBtn.addActionListener(e -> {
			int selectedRow = courseTable.getSelectedRow();
			if (selectedRow != -1) {
				int modelRow = courseTable.convertRowIndexToModel(selectedRow);
				Course selectedCourse = tableModel.getCourseAt(modelRow);
				student.removeCourse(selectedCourse);
				tableModel.fireTableDataChanged();
			} else {
				JOptionPane.showMessageDialog(view, "Please select a course to remove.");
			}
		});

		scheduleBtn.addActionListener(e -> {
			StringBuilder sb = new StringBuilder();
			for (Course c : student.getSchedule().getCourses()) {
				sb.append(c).append("\n");
			}
			JOptionPane.showMessageDialog(view, sb.toString(), "My Schedule", JOptionPane.INFORMATION_MESSAGE);
		});

		backBtn.addActionListener(e -> view.showPanel("HOME"));
	}

	private void showAdminPanel(Admin admin) {
		JPanel panel = new JPanel(new BorderLayout());
		JLabel header = new JLabel("Admin: " + admin.getName(), SwingConstants.CENTER);
		panel.add(header, BorderLayout.NORTH);

		CourseTableModel tableModel = new CourseTableModel(system.getCourses());
		JTable courseTable = new JTable(tableModel);
		courseTable.setAutoCreateRowSorter(true); //Enable sorting

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

		addBtn.addActionListener(e -> showAddCourseDialog(admin, tableModel));
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

		backBtn.addActionListener(e -> view.showPanel("HOME"));
	}

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

		int result = JOptionPane.showConfirmDialog(null, fields, "Add Course", JOptionPane.OK_CANCEL_OPTION);

		if(result == JOptionPane.OK_OPTION) {
			Course c = new Course(
				idField.getText(),
				nameField.getText(),
				instrField.getText(),
				dayField.getText(),
				startField.getText(),
				endField.getText(),
				Integer.parseInt(creditsField.getText()),
				Integer.parseInt(capField.getText())
			);

			admin.addCourse(c);
			system.addCourse(c);
			tableModel.addCourse(c);
		}
	}
}