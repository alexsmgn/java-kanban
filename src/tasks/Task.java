package tasks;

import status.Status;

import java.util.Objects;

public class Task {
    private int id;
    private String title;
    private String target;
    private Status status;

    public Task(String title, String target) {
        this.title = title;
        this.target = target;
        status = status.NEW;
    }

    public Task(String target, String title, Status status) {
        this.title = title;
        this.status = status;
        this.target = target;
    }

    public Task(int id, String title, Status status, String target) {
        this.id = id;
        this.title = title;
        this.status = status;
        this.target = target;
    }

    public Task(String title, Status status, String target) {
        this.title = title;
        this.status = status;
        this.target = target;

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

    public Tasks getType() {
        return Tasks.SIMPLETASK;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", target='" + target + '\'' +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
