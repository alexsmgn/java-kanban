package tasks;

import status.Status;

public class SimpleTask extends Task {

    public SimpleTask(String title, String target) {
        super(title, target);
    }

    public SimpleTask(int id, String title, Status status, String target) {
        super(id, title, status, target);
    }

    public SimpleTask(String target, String title, Status status) {
        super(target, title, status);
    }

    @Override
    public Tasks getType() {
        return Tasks.SIMPLETASK;
    }

}
