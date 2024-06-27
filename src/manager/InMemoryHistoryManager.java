package manager;

import tasks.Task;
import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    public final List<Task> history = new ArrayList<>(10);

    @Override
    public ArrayList<Task> getHistory() {
        return new ArrayList<>(history);
    }

    @Override
    public void add(Task task) {
        if (task != null) {
            if (history.size() >= 10) {
                history.removeFirst();
            }
            history.add(task);
        }
    }
}
