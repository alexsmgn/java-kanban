package manager;

import tasks.Task;
import java.util.ArrayList;
import java.util.List;

public interface HistoryManager {

    List<Task> getHistory();

    void add(Task task);
}
