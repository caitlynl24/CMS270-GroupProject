package finalProject;

public class SchedulerController {
	SchedulerView view;
	
	public SchedulerController(SchedulerView v) {
		this.view = v;
		
		v.studentButton.addActionListener(e -> {
			String id = v.id.getText();
			String name = v.name.getText();
		});
	}
}
