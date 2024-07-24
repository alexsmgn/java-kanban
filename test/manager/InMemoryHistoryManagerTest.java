package manager;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import tasks.Task;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

    private final HistoryManager historyManager = new InMemoryHistoryManager();
    static List<Task> history;
    static Task task;
    static Task task1;

    @BeforeAll
    static void createTask() {
        task = new Task("title", "target");
        task1 = new Task("title", "target");
        history = new ArrayList<>();

        task.setId(1);
        task1.setId(2);
    }

    @Test
    void add() {
        historyManager.add(task);
        history = historyManager.getHistory();
        assertNotNull(history, "История не пустая.");
        assertEquals(1, history.size(), "История не пустая.");
    }

    @Test
    void remove() {
        historyManager.add(task);
        historyManager.add(task1);
        historyManager.remove(task.getId());

        assertEquals(historyManager.getHistory(), List.of(task));
    }
}