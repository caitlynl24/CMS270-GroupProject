package finalProject;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Table model for displaying a collection of {@link Course} objects
 * inside a Swing {@link javax.swing.JTable}.
 * 
 * <p>Supports sorting by department and provides easy retrieval of
 * course data based on table rows.</p>
 */
public class CourseTableModel extends AbstractTableModel{

    // Column headers for the table.
    private final String[] columnNames = {
        "Course ID", "Course Name", "Instructor", 
        "Days", "Start-End", "Credits", "Seats Left"
    };

    // Internal course list used for table display.
    private List<Course> courses;

    /**
     * Creates the model using an initial list of courses.
     * The list is copied and sorted alphabetically by department.
     * 
     * @param courses original list of courses
     */
    public CourseTableModel(List<Course> courses) {
        this.courses = new ArrayList<>(courses);

        //Sort by department prefix, then by course ID
        this.courses.sort((c1, c2) -> {
            int cmp = c1.getDepartment().compareToIgnoreCase(c2.getDepartment());
            if(cmp == 0) return c1.getCourseId().compareTo(c2.getCourseId());
            return cmp;
        });
    }

    // -------------- TableModel Overrides --------------

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
            case 0: return c.getCourseId();
            case 1: return c.getCourseName();
            case 2: return c.getInstructor();
            case 3: return c.getDay();
            case 4: return c.getStartTime() + " - " + c.getEndTime();
            case 5: return c.getCredits();
            case 6: return c.seatsLeft();
            default: return "";
        }
    }

    // ------------ Utility Accessors --------------

    /**
     * Retrieves the full Course object associated with a table row.
     * 
     * @param row row index
     * @return course at the given row
     */
    public Course getCourseAt(int row) {
        return courses.get(row);
    }

    /**
     * Adds a new course and re-sorts the list automatically.
     * 
     * @param c course to add
     */
    public void addCourse(Course c) {
        courses.add(c);

        // Re-sort after adding
        courses.sort((c1, c2) -> {
            int cmp = c1.getDepartment().compareToIgnoreCase(c2.getDepartment());
            if(cmp == 0) return c1.getCourseId().compareTo(c2.getCourseId());
            return cmp;
        });

        fireTableDataChanged();
    }

    /**
     * Removes a course and refreshes the display.
     * 
     * @param c course to remove
     */
    public void removeCourse(Course c) {
        courses.remove(c);
        fireTableDataChanged();
    }
}