package finalProject;

import java.awt.*;
import javax.swing.*;

public class SchedulerView extends JFrame {
	private final Color BLUE = new Color(0, 102, 204);
	private final Color GOLD = new Color(255, 204, 0);

	private JLabel welcomeLabel = new JLabel("Welcome to the Course Scheduler System");
	private JLabel idLabel = new JLabel("ID:");
	private JLabel nameLabel = new JLabel("Name:");
	private JLabel choiceLabel = new JLabel("Select role:");
	
	private JButton studentButton = new JButton("Student");
	private JButton adminButton = new JButton("Admin");
	
	private JTextField name = new JTextField(10);
	private JTextField id = new JTextField(10);

	private JPanel mainPanel = new JPanel(new CardLayout());
	private JPanel homePanel = new JPanel();
	
	public SchedulerView() {
		// Create first page
		setTitle("Course Scheduler System");
		setSize(650, 550);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		styleComponents();
		buildHomePanel();

		mainPanel.add(homePanel, "HOME");
		add(mainPanel);

		setVisible(true);
	}

	private void styleComponents() {
		welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
		welcomeLabel.setForeground(BLUE);

		studentButton.setBackground(BLUE);
		studentButton.setForeground(Color.WHITE);
		studentButton.setFocusPainted(false);

		adminButton.setBackground(GOLD);
		adminButton.setForeground(Color.BLACK);
		adminButton.setFocusPainted(false);
	}

	private void buildHomePanel() {
		homePanel.setLayout(new GridBagLayout());
		homePanel.setBackground(Color.WHITE);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.insets = new Insets(10, 0, 10, 0);
		
		//Add the R logo (vector drawn)
		homePanel.add(new RLogo(), gbc);

		homePanel.add(welcomeLabel, gbc);
		homePanel.add(idLabel, gbc);
		homePanel.add(id, gbc);
		homePanel.add(nameLabel, gbc);
		homePanel.add(name, gbc);
		homePanel.add(choiceLabel, gbc);
		homePanel.add(studentButton, gbc);
		homePanel.add(adminButton, gbc);
	}

	public JButton getStudentButton() {
		return studentButton;
	}

	public JButton getAdminButton() {
		return adminButton;
	}

	public JTextField getIdField() {
		return id;
	}

	public JTextField getNameField() {
		return name;
	}

	public JPanel getMainPanel() {
		return mainPanel;
	}

	public void showPanel(String panelName) {
		CardLayout cl = (CardLayout) mainPanel.getLayout();
		cl.show(mainPanel, panelName);
	}
}