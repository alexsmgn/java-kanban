package tasks;

import status.Status;

import java.util.ArrayList;

public class Epic extends Task {

    private final ArrayList<Integer> subTaskIds = new ArrayList<>();

    public Epic(String target, String title) {
        super(title, target);
    }

    public Epic(int id, String title, Status status, String target) {
        super(id, title, status, target);
    }

    public Epic(String target, String title, Status status) {
       super(target, title, status);
    }

    public ArrayList<Integer> getSubTaskIds() {
        return subTaskIds;
    }

    public void setSubTaskIds(int id) {
        subTaskIds.add(id);
    }

    public void remSubtask(SubTask subTask) {
        subTaskIds.remove(Integer.valueOf(subTask.getId()));
    }

    public void remAllSubTasks() {
        subTaskIds.clear();
    }

    public void addSubTask(SubTask subTask) {
        subTaskIds.add(subTask.getId());
    }

    @Override
    public Tasks getType() {
        return Tasks.EPIC;
    }


}
