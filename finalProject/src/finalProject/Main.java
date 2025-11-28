package finalProject;

public class Main {
    public static void main(String[] args) {
        SchedulerSystem system = new SchedulerSystem();
        system.loadSampleData();

        SchedulerView view = new SchedulerView();
        new SchedulerController(view, system);
    }
}