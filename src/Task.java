public class Task {
    private int id;
    private String title;
    private String target;
    private Status status;

    public Task(int id, String title, String target, Status status) {
        this.id = id;
        this.title = title;
        this.target = target;
        this.status = status;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


    public enum Status {NEW, IN_PROGRESS, DONE}

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", target='" + target + '\'' +
                ", status=" + status +
                '}';
    }
}
