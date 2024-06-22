package tasks;

public class SubTask extends Task {

    private final int epicId;

    public SubTask(int id, String title, String target, int epicId) {
        super(id, title, target);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }







}
