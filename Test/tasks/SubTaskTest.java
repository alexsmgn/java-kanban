package tasks;

import manager.InMemoryTaskManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubTaskTest {

    InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();

    @Test
    public void equalsSubTaskId() {

        SubTask subTask = new SubTask("Title", "Target", 1);
        SubTask subTask1 = new SubTask("title1", "target1", 1);

        inMemoryTaskManager.addSubTask(subTask);
        inMemoryTaskManager.addSubTask(subTask1);
        subTask1.setId(1);
        assertEquals(subTask, subTask1, "Это не одна задача");
    }
}