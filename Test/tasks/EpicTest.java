package tasks;

import manager.InMemoryTaskManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {
    InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();

@Test
    public void equalsEpicId() {
    Epic epic = new Epic("Title", "Target");
    Epic epic1 = new Epic("Title1", "Target1");

    inMemoryTaskManager.addEpic(epic);
    inMemoryTaskManager.addEpic(epic1);
    epic1.setId(1);
    assertEquals(epic, epic1, "Это не одна задача");
}
}