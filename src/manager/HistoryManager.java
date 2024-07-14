package manager;

import tasks.Task;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface HistoryManager {

    List<Task> getHistory();

    void add(Task task);

    void remove(int id);
}
