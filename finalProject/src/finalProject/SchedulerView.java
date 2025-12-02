package finalProject;

import java.awt.*;
import javax.swing.*;

/**
 * Primary GUI view for the Course Scheduler System.
 * 
 * <p>This View class displays the initial login screen where users enter
 * their ID, name, and select their role (Student or Admin). It also manages
 * the main container panel that uses a {@link CardLayout}, allowing the
 * controller to swap between different screens such as Student panel,
 * Admin panel, etc.</p>
 * 
 * <p>Note: This class does *not* contain any business logic. All actions
 * are handled externally in {@link SchedulerController}. The View simply
 * provides components and manages visual layout.</p>
 */
public class SchedulerView extends JFrame {

	// Branding colors for UI consistency
	private final Color BLUE = new Color(0, 102, 204);
	private final Color GOLD = new Color(255, 204, 0);

	//Display labels on the home/login panel.
	private JLabel welcomeLabel = new JLabel("Welcome to the Course Scheduler System");
	private JLabel idLabel = new JLabel("ID:");
	private JLabel nameLabel = new JLabel("Name:");
	private JLabel choiceLabel = new JLabel("Select role:");
	
	// Login buttons allowing user to choose Student or Admin mode.
	private JButton studentButton = new JButton("Student");
	private JButton adminButton = new JButton("Admin");
	
	// Input fields for user ID and name.
	private JTextField name = new JTextField(10);
	private JTextField id = new JTextField(10);

	// Main application panel controlled using CardLayout.
	private JPanel mainPanel = new JPanel(new CardLayout());

	// Home screen panel displayed on startup.
	private JPanel homePanel = new JPanel();
	
	/**
	 * Constructs the main application window and initializes all components.
	 */
	public SchedulerView() {
		setTitle("Course Scheduler System");
		setSize(1280, 720);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		styleComponents();
		buildHomePanel();

		// Add home panel as first screen in the main CardLayout panel
		mainPanel.add(homePanel, "HOME");
		add(mainPanel);

		setVisible(true);
	}

	/**
	 * Applies color, font, and styling properties to components.
	 */
	private void styleComponents() {
		welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
		welcomeLabel.setForeground(BLUE);

		studentButton.setFocusPainted(false);
		adminButton.setFocusPainted(false);
	}

	/**
	 * Builds the home/login panel layout, including:
	 * <ul>
	 *   <li>Welcome label</li>
	 *   <li>ID and Name fields</li>
	 *   <li>Student/Admin buttons</li>
	 * </ul>
	 * 
	 * <p>Uses GridBagLayout to center and vertically stack components.</p>
	 */
	private void buildHomePanel() {
		homePanel.setLayout(new GridBagLayout());
		homePanel.setBackground(Color.WHITE);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.insets = new Insets(10, 0, 10, 0);

		homePanel.add(welcomeLabel, gbc);
		homePanel.add(idLabel, gbc);
		homePanel.add(id, gbc);
		homePanel.add(nameLabel, gbc);
		homePanel.add(name, gbc);
		homePanel.add(choiceLabel, gbc);
		homePanel.add(studentButton, gbc);
		homePanel.add(adminButton, gbc);
	}

	// ----------------------------------------
	// PUBLIC ACCESSORS (used by SchedulerController)
	// ----------------------------------------

	/**
	 * @return the Student button used for login.
	 */
	public JButton getStudentButton() {
		return studentButton;
	}

	/**
	 * @return the Admin button used for login.
	 */
	public JButton getAdminButton() {
		return adminButton;
	}

	/**
	 * @return the text field containing the user-entered ID.
	 */
	public JTextField getIdField() {
		return id;
	}

	/**
	 * @return the text field containing the user-entered name.
	 */
	public JTextField getNameField() {
		return name;
	}

	/**
	 * @return the main CardLayout-based panel used to swap screens.
	 */
	public JPanel getMainPanel() {
		return mainPanel;
	}

	/**
	 * Switches the visible screen within the {@link CardLayout}.
	 * 
	 * @param panelName the name of the panel to show (e.g., "HOME", "STUDENT", "ADMIN")
	 */
	public void showPanel(String panelName) {
		CardLayout cl = (CardLayout) mainPanel.getLayout();
		cl.show(mainPanel, panelName);
	}
}