package tasks;

import status.Status;

import java.time.Duration;
import java.time.LocalDateTime;

public class SimpleTask extends Task {

    public SimpleTask(String title, String target) {
        super(title, target);
    }

    public SimpleTask(int id, String title, Status status, String target, LocalDateTime startTime, Duration duration) {
        super(id, title, status, target, startTime, duration);
    }

    public SimpleTask(String target, String title, Status status) {
        super(target, title, status);
    }

    @Override
    public Tasks getType() {
        return Tasks.SIMPLETASK;
    }

}
