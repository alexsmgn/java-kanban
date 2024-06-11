public class SubTask extends Task {

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    int epicId;


    public SubTask(int id, String title, String target, Status status, int epicId) {
        super(id, title, target, status);
        this.epicId = epicId;
    }
}
