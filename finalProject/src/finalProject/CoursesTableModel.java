package finalProject;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class CoursesTableModel extends AbstractTableModel {
	
	// Array of courses for table
	private ArrayList<Course> courses;
	
	private String[] columnNames = new String[] {
		"Course ID", "Course Name", "Instructor", "Day",
		"Start Time", "End Time", "Credits", "Capacity"
	};
	
	// Type of each column
	private Class[] columnClasses = new Class[] {
		String.class, String.class, String.class, String.class,
		String.class, String.class, int.class, int.class
	};

	// CoursesTableModel constructor sets up table
	public CoursesTableModel(ArrayList<Course> courses) {
		this.courses = courses;
	}

	@Override
	public int getRowCount() {
		return courses.size();
	}

	@Override
	public int getColumnCount() {
		return 8;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Course c = courses.get(rowIndex);
		switch (columnIndex) {
		case 0: return c.getCourseId();
		case 1: return c.getCourseName();
		case 2: return c.getInstructor();
		case 3: return c.getDay();
		case 4: return SchedulerSystem.formatTime12(c.getStartTime());
		case 5: return SchedulerSystem.formatTime12(c.getEndTime());
		case 6: return c.getCredits();
		case 7: return c.getCapacity();
		default: return null;
		}
	}

	@Override
	public String getColumnName(int col) {
        return columnNames[col];
    }
	
	@Override
	public Class<?> getColumnClass(int col) {
        return columnClasses[col];
    }

	public Course getCourseAt(int modelRow) {
		return courses.get(modelRow);
	}

	public void removeCourse(Course c) {
		courses.remove(c);
	}

	public void addCourse(Course c) {
		courses.add(c);
	}
	
}
