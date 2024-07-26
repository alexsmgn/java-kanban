package manager;

import tasks.Task;

import java.util.List;


public class InMemoryHistoryManager implements HistoryManager {

    public final LinkedList history = new LinkedList();

    @Override
    public List<Task> getHistory() {
        return history.getTaskList();
    }

    @Override
    public void add(Task task) {
        history.addLast(task);
    }

    @Override
    public void remove(int id) {
        history.removeNode(history.customMap.get(id));
    }
}



