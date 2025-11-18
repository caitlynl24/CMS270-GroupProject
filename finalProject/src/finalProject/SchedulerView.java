package finalProject;

import java.awt.*;
import javax.swing.*;

public class SchedulerView extends JFrame {
	JLabel welcomeLabel = new JLabel("Welcome to the Course Scheduler System");
	JLabel idLabel = new JLabel("ID:");
	JLabel nameLabel = new JLabel("Name:");
	JLabel choiceLabel = new JLabel("Select role:");
	
	JButton studentButton = new JButton("Student");
	JButton adminButton = new JButton("Admin");
	
	JTextField name = new JTextField(10);
	JTextField id = new JTextField(10);
	
	public SchedulerView() {
		// Create first page
		setTitle("Course Scheduler System");
		setSize(500, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.insets = new Insets(30, 0, 0, 0);
		
		panel.add(welcomeLabel, gbc);
		panel.add(idLabel);
		panel.add(id, gbc);
		panel.add(nameLabel);
		panel.add(name, gbc);
		panel.add(choiceLabel, gbc);
		panel.add(studentButton, gbc);
		panel.add(adminButton, gbc);
		
		add(panel);
		setVisible(true);
	}
}
