package finalProject;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class CourseTableModel extends AbstractTableModel{
    private final String[] columnNames = {
        "Course ID", "Course Name", "Instructor", "Days", "Start-End", "Credits", "Seats Left"
    };
    private List<Course> courses;

    public CourseTableModel(List<Course> courses) {
        this.courses = new ArrayList<>(courses);
        //Sort alphabetically by depatment prefix
        this.courses.sort((c1, c2) -> c1.getDepartment().compareToIgnoreCase(c2.getDepartment()));
    }

    @Override
    public int getRowCount() {
        return courses.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Course c = courses.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return c.getCourseId();
            case 1:
                return c.getCourseName();
            case 2:
                return c.getInstructor();
            case 3:
                return c.getDay();
            case 4:
                return c.getStartTime() + " - " + c.getEndTime();
            case 5:
                return c.getCredits();
            case 6:
                return c.seatsLeft();
            default:
                return "";
        }
    }

    public Course getCourseAt(int row) {
        return courses.get(row);
    }

    public void addCourse(Course c) {
        courses.add(c);
        courses.sort((c1, c2) -> c1.getDepartment().compareTo(c2.getDepartment()));
        fireTableDataChanged();
    }

    public void removeCourse(Course c) {
        courses.remove(c);
        fireTableDataChanged();
    }
}