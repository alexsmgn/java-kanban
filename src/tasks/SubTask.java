package tasks;

public class SubTask extends Task {

    private final int epicId;

    public SubTask(String title, String target, int epicId) {
        super(title, target);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

}
