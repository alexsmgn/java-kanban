package tasks;

import manager.InMemoryTaskManager;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SimpleTaskTest {
    InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();

    @Test
    public void equalSimpleTaskId() {
        SimpleTask simpleTask = new SimpleTask("Title", "Target");
        SimpleTask simpleTask1 = new SimpleTask("Title", "Target");

        inMemoryTaskManager.addTask(simpleTask);
        inMemoryTaskManager.addTask(simpleTask1);
        simpleTask1.setId(1);
        assertEquals(simpleTask, simpleTask1, "Это не одна задача");
}
}