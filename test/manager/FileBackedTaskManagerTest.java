package manager;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileBackedTaskManagerTest extends TaskManagerTests<FileBackedTaskManager> {

    File file;

    FileBackedTaskManagerTest() throws Exception {
        file = File.createTempFile("Tasks", ".csv");
        taskManager = new FileBackedTaskManager(file);
    }

    @Test
    void loadFromFile() {
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(file);

        simpleTask.setStartTime(LocalDateTime.of(2024, 8, 18, 15, 0));
        simpleTask.setDuration(Duration.ofMinutes(15));
        fileBackedTaskManager.addTask(simpleTask);

        epic.setStartTime(LocalDateTime.of(2024, 8, 18, 16, 0));
        epic.setDuration(Duration.ofMinutes(15));
        fileBackedTaskManager.addEpic(epic);

        subTask.setStartTime(LocalDateTime.of(2024, 8, 18, 17, 0));
        subTask.setDuration(Duration.ofMinutes(15));
        fileBackedTaskManager.addSubTask(subTask); // Инициализируем менеджер и добавляем задачи в тестовый файл

        assertEquals(1, fileBackedTaskManager.simpleTasks.size()); // проверяем, добавились ли задачи в списки
        assertEquals(1, fileBackedTaskManager.epics.size());
        assertEquals(1, fileBackedTaskManager.subTasks.size());

        FileBackedTaskManager fileLoader = FileBackedTaskManager.loadFromFile(file);

        assertEquals(fileBackedTaskManager.printAllSimpleTasks(), fileLoader.printAllSimpleTasks());
        assertEquals(fileBackedTaskManager.printAllEpics(), fileLoader.printAllEpics());
        assertEquals(fileBackedTaskManager.printAllSubTasks(), fileLoader.printAllSubTasks());

        assertEquals(fileBackedTaskManager.getPrioritizedTasks(), fileLoader.getPrioritizedTasks());
    }
}
