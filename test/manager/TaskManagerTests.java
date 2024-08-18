package manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.SimpleTask;
import tasks.SubTask;
import tasks.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

abstract class TaskManagerTests<T extends TaskManager> {

    protected T taskManager;
    protected SimpleTask simpleTask;
    protected Epic epic;
    protected SubTask subTask;

    @BeforeEach
    void beforeEach() {
        simpleTask = new SimpleTask("Title", "Target");
        simpleTask.setStartTime(LocalDateTime.now());
        simpleTask.setDuration(Duration.ofHours(4));

        epic = new Epic("Target", "Title");

        subTask = new SubTask("Target", "Title", 2);
        subTask.setStartTime(LocalDateTime.now().plusDays(2));
        subTask.setDuration(Duration.ofHours(2));
    }

    @Test
    void addNewSimpleTask() {
        final int simpleTaskId = taskManager.addTask(simpleTask);
        final SimpleTask savedSimpleTask = taskManager.printSimpleTaskById(simpleTaskId);

        assertNotNull(savedSimpleTask, "Задача не найдена");
        assertEquals(simpleTask, savedSimpleTask, "Задачи не совпадают");

        final List<Task> simpleTasks = taskManager.printAllSimpleTasks();

        assertNotNull(simpleTasks, "Задачи не возвращаются");
        assertEquals(1, simpleTasks.size(), "Не верное количество задач");
        assertEquals(simpleTask, simpleTasks.get(0), "Задачи не совпадают");
    }

    @Test
    void addNewEpic() {
        final int epicId = taskManager.addEpic(epic);
        final Epic savedEpic = taskManager.printEpicById(epicId);

        assertNotNull(savedEpic, "Задача не найдена");
        assertEquals(epic, savedEpic, "Задачи не совпадают");

        final List<Epic> epics = taskManager.printAllEpics();

        assertNotNull(epics, "Задачи не возвращаются");
        assertEquals(1, epics.size(), "Не верное количество эпиков");
        assertEquals(epic, epics.get(0), "Задачи не совпадают");
    }

    @Test
    void addSubTask() {
        taskManager.addEpic(epic);
        final int subTaskId = taskManager.addSubTask(subTask);
        final SubTask savedSubTask = taskManager.printSubTaskById(subTaskId);

        assertNotNull(savedSubTask, "Задача не найдена");
        assertEquals(subTask, savedSubTask, "Задачи не совпадают");

        final List<SubTask> subTasks = taskManager.printAllSubTasks();

        assertNotNull(subTasks, "Задачи не возвращаются");
        assertEquals(1, subTasks.size(), "Не верное количество задач");
        assertEquals(subTask, subTasks.get(0), "Задачи не совпадают");
    }

    @Test
    void delSimpleTaskById() {
        List<Task> simpleTasks = new ArrayList<>();
        simpleTask.setId(1);
        simpleTasks.add(simpleTask);
        taskManager.delSimpleTaskById(1);
        simpleTasks = taskManager.printAllSimpleTasks();

        assertTrue(simpleTasks.isEmpty(), "Список пуст");
    }

    @Test
    void delEpicById() {
        List<Epic> epics = new ArrayList<>();
        simpleTask.setId(1);
        epics.add(epic);
        taskManager.delEpicById(1);
        epics = taskManager.printAllEpics();

        assertTrue(epics.isEmpty(), "Список пуст");
    }

    @Test
    void delSubTaskById() {
        List<SubTask> subTasks = new ArrayList<>();
        subTask.setId(1);
        subTasks.add(subTask);
        taskManager.delSubTaskById(1);
        subTasks = taskManager.printAllSubTasks();

        assertTrue(subTasks.isEmpty(), "Список пуст");
    }

    @Test
    void clearAllTasks() {
        taskManager.clearAllTasks();
        List<Task> tasks = taskManager.printAllSimpleTasks();
        assertTrue(tasks.isEmpty(), "Список пуст");
    }

    @Test
    void clearAllEpics() {
        taskManager.clearAllEpics();
        List<Epic> epics = taskManager.printAllEpics();
        assertTrue(epics.isEmpty(), "Список пуст");
    }

    @Test
    void clearAllSubTasks() {
        taskManager.clearAllSubTasks();
        List<SubTask> subTasks = taskManager.printAllSubTasks();
        assertTrue(subTasks.isEmpty(), "Список пуст");
    }

    @Test
    void getPrioritized() {
        simpleTask.setStartTime(LocalDateTime.now());
        simpleTask.setDuration(Duration.ofMinutes(15));
        taskManager.addTask(simpleTask);

        taskManager.addEpic(epic);

        subTask.setStartTime(LocalDateTime.now().minusHours(1));
        subTask.setDuration(Duration.ofMinutes(15));
        taskManager.addSubTask(subTask);

        assertEquals(taskManager.getPrioritizedTasks(), List.of(subTask, simpleTask));
    }


}
