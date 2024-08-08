package manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import status.Status;
import tasks.SimpleTask;
import tasks.Epic;
import tasks.SubTask;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileBackedTaskManagerTest {

    File file;
    SimpleTask simpleTask;
    Epic epic;
    SubTask subTask;

    @BeforeEach
    void beforeEach() throws IOException {
        file = file.createTempFile("Tasks", ".csv");
        simpleTask = new SimpleTask("target", "title", Status.IN_PROGRESS);
        epic = new Epic("target", "title", Status.IN_PROGRESS);
        subTask = new SubTask("target", "title", Status.IN_PROGRESS, 2);
    }

    @Test
    void loadFromFile() {
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(file);

        fileBackedTaskManager.addTask(simpleTask);
        fileBackedTaskManager.addEpic(epic);
        fileBackedTaskManager.addSubTask(subTask); // Инициализируем менеджер и добавляем задачи в тестовый файл

        assertEquals(1, fileBackedTaskManager.simpleTasks.size()); // проверяем, добавились ли задачи в списки
        assertEquals(1, fileBackedTaskManager.epics.size());
        assertEquals(1, fileBackedTaskManager.subTasks.size());

        FileBackedTaskManager fileLoader = FileBackedTaskManager.loadFromFile(file);

        assertEquals(fileBackedTaskManager.printAllSimpleTasks(), fileLoader.printAllSimpleTasks());
        assertEquals(fileBackedTaskManager.printAllEpics(), fileLoader.printAllEpics());
        assertEquals(fileBackedTaskManager.printAllSubTasks(), fileLoader.printAllSubTasks());
    }
}
