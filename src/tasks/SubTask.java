package tasks;

import status.Status;

public class SubTask extends Task {

    private final int epicId;

    public SubTask(String target, String title, int epicId) {
        super(title, target);
        this.epicId = epicId;
    }

    public SubTask(String target, String title, Status status, int epicId) {
        super(title, status, target);
        this.epicId = epicId;
    }

    public SubTask(int id, String title, Status status, String target, int epicId) {
        super(id, title, status, target);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public Tasks getType() {
        return Tasks.SUBTASK;
    }

}
