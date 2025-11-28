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
			String id = view.getIdField().getText();
			String name = view.getNameField().getText();
			
			if(id.isEmpty() || name.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Please enter ID and Name.");
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
			String id = view.getIdField().getText();
			String name = view.getNameField().getText();
			
			if(id.isEmpty() || name.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Please enter ID and Name.");
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

		DefaultListModel<String> courseModel = new DefaultListModel<>();
		JList<String> courseList = new JList<>(courseModel);
		
		for (Course c : system.getCourses()) {
			courseModel.addElement(c.toString());
		}

		panel.add(new JScrollPane(courseList), BorderLayout.CENTER);

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
			String courseId = JOptionPane.showInputDialog("Enter Course ID:");
			Course c = system.findCourse(courseId);

			if (c != null) student.addCourse(c);
			else JOptionPane.showMessageDialog(null, "Course Not Found");
		});

		removeBtn.addActionListener(e -> {
			String courseId = JOptionPane.showInputDialog("Enter Course ID:");
			Course c = system.findCourse(courseId);

			if (c != null) student.removeCourse(c);
			else JOptionPane.showMessageDialog(null, "Course Not Found");
		});

		scheduleBtn.addActionListener(e -> {
			StringBuilder sb = new StringBuilder();
			for (Course c : student.getSchedule().getCourses()) {
				sb.append(c).append("\n");
			}
			JOptionPane.showMessageDialog(null, sb.toString(), "Your Schedule", JOptionPane.INFORMATION_MESSAGE);
		});

		backBtn.addActionListener(e -> view.showPanel("HOME"));
	}

	private void showAdminPanel(Admin admin) {
		JPanel panel = new JPanel(new BorderLayout());
		JLabel header = new JLabel("Admin: " + admin.getName(), SwingConstants.CENTER);
		panel.add(header, BorderLayout.NORTH);

		DefaultListModel<String> courseModel = new DefaultListModel<>();
		JList<String> courseList = new JList<>(courseModel);
		
		for (Course c : system.getCourses()) {
			courseModel.addElement(c.toString());
		}

		panel.add(new JScrollPane(courseList), BorderLayout.CENTER);

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

		addBtn.addActionListener(e -> showAddCourseDialog(admin, courseModel));

		removeBtn.addActionListener(e -> {
			String id = JOptionPane.showInputDialog("Enter Course ID:");
			Course c = system.findCourse(id);

			if (c != null) {
				system.removeCourse(c);
				admin.removeCourse(c);
				courseModel.removeElement(c.toString());
			} else {
				JOptionPane.showMessageDialog(null, "Course Not Found");
			}
		});

		backBtn.addActionListener(e -> view.showPanel("HOME"));
	}

	private void showAddCourseDialog(Admin admin, DefaultListModel<String> model) {
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
			model.addElement(c.toString());
		}
	}
}