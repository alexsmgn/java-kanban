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



    @BeforeAll
    static void createTask() {
        task = new Task("title", "target");
        history = new ArrayList<>();
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
        task.setId(1);
        historyManager.add(task);
        assertNotNull(history, "История не пустая.");
        historyManager.remove(1);
        history = historyManager.getHistory();
        assertTrue(history.contains(task), "Задача удалена.");


    }
}