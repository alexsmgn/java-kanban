package tasks;

import status.Status;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Epic extends Task {

    private final ArrayList<Integer> subTaskIds = new ArrayList<>();
    private LocalDateTime endTime;

    public Epic(String target, String title) {
        super(title, target);
    }

    public Epic(int id, String title, Status status, String target, LocalDateTime startTime, LocalDateTime endTime,
                Duration duration) {
        super(id, title, status, target, startTime, duration);
        this.endTime = endTime;
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


    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public Duration getDuration() {
        return super.getDuration();
    }

    @Override
    public void setDuration(Duration duration) {
        super.setDuration(duration);
    }

    @Override
    public LocalDateTime getStartTime() {
        return super.getStartTime();
    }

    @Override
    public void setStartTime(LocalDateTime startTime) {
        super.setStartTime(startTime);
    }
}
