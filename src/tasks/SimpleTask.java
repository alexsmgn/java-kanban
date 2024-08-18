package tasks;

import status.Status;

import java.time.Duration;
import java.time.LocalDateTime;

public class SimpleTask extends Task {

    public SimpleTask(String target, String title) {
        super(target, title);
    }

    public SimpleTask(int id, String title, Status status, String target, LocalDateTime startTime, Duration duration) {
        super(id, title, status, target, startTime, duration);
    }

    @Override
    public Tasks getType() {
        return Tasks.SIMPLETASK;
    }

}
