public class SubTask extends Task {

    private int epicId;

    public SubTask(int id, String title, String target, int epicId) {
        super(id, title, target, Status.NEW);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }





}
