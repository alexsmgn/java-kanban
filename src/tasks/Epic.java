package tasks;
import java.util.ArrayList;

public class Epic extends Task {

    private final ArrayList<Integer> subTaskIds;

    public Epic(int id, String title, String target) {
        super(id, title, target);
        subTaskIds = new ArrayList<>();
    }

    public ArrayList<Integer> getSubTaskIds() {
        return subTaskIds;
    }

    public void setSubTaskIds(int id) {
        subTaskIds.add(id);
    }

    public void remSubtask (SubTask subTask) {
       subTaskIds.remove(Integer.valueOf(subTask.getId()));
    }

    public void remAllSubTasks () {
        subTaskIds.clear();
    }

    public void addSubTask (SubTask subTask) {
        subTaskIds.add(subTask.getId());
    }
}
