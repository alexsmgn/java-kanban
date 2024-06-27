package manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.SimpleTask;
import tasks.SubTask;
import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {

    private static InMemoryTaskManager inMemoryTaskManager;
    private static SimpleTask simpleTask;
    private static Epic epic;
    private static SubTask subTask;
    private static final int nextId = 1;

    @BeforeEach
    void createAll() {
        inMemoryTaskManager = new InMemoryTaskManager();
        simpleTask = new SimpleTask("title", "target");
        epic = new Epic("title", "target");
        subTask = new SubTask("title", "target", 1);
    }

    @Test
    void addNewSimpleTask() {
        final int simpleTaskId = inMemoryTaskManager.addTask(simpleTask);
        final SimpleTask savedSimpleTask = inMemoryTaskManager.printSimpleTaskById(simpleTaskId);

        assertNotNull(savedSimpleTask, "Задача не найдена");
        assertEquals(simpleTask, savedSimpleTask, "Задачи не совпадают");

        final List<Task> simpleTasks = inMemoryTaskManager.printAllSimpleTasks();

        assertNotNull(simpleTasks, "Задачи не возвращаются");
        assertEquals(1, simpleTasks.size(), "Не верное количество задач");
        assertEquals(simpleTask, simpleTasks.get(0), "Задачи не совпадают");
    }

    @Test
    void addNewEpic() {
        final int epicId = inMemoryTaskManager.addEpic(epic);
        final Epic savedEpic = inMemoryTaskManager.printEpicById(epicId);

        assertNotNull(savedEpic, "Задача не найдена");
        assertEquals(epic, savedEpic, "Задачи не совпадают");

        final List<Epic> epics = inMemoryTaskManager.printAllEpics();

        assertNotNull(epics, "Задачи не возвращаются");
        assertEquals(1, epics.size(), "Не верное количество эпиков");
        assertEquals(epic, epics.get(0), "Задачи не совпадают");
    }

    @Test
    void addSubTask() {
        inMemoryTaskManager.addEpic(epic);
        final int subTaskId = inMemoryTaskManager.addSubTask(subTask);
        final SubTask savedSubTask = inMemoryTaskManager.printSubTaskById(subTaskId);

        assertNotNull(savedSubTask, "Задача не найдена");
        assertEquals(subTask, savedSubTask, "Задачи не совпадают");

        final List<SubTask> subTasks = inMemoryTaskManager.printAllSubTasks();

        assertNotNull(subTasks, "Задачи не возвращаются");
        assertEquals(1, subTasks.size(), "Не верное количество задач");
        assertEquals(subTask, subTasks.get(0), "Задачи не совпадают");
    }

    @Test
    void delSimpleTaskById() {
        ArrayList<Task> simpleTasks = new ArrayList<>();
        simpleTask.setId(1);
        simpleTasks.add(simpleTask);
        inMemoryTaskManager.delSimpleTaskById(1);
        simpleTasks = inMemoryTaskManager.printAllSimpleTasks();

        assertTrue(simpleTasks.isEmpty(), "Список пуст");
    }

    @Test
    void delEpicById() {
        ArrayList<Epic> epics = new ArrayList<>();
        simpleTask.setId(1);
        epics.add(epic);
        inMemoryTaskManager.delEpicById(1);
        epics = inMemoryTaskManager.printAllEpics();

        assertTrue(epics.isEmpty(), "Список пуст");
    }

    @Test
    void delSubTaskById() {
        ArrayList<SubTask> subTasks = new ArrayList<>();
        subTask.setId(1);
        subTasks.add(subTask);
        inMemoryTaskManager.delSubTaskById(1);
        subTasks = inMemoryTaskManager.printAllSubTasks();

        assertTrue(subTasks.isEmpty(), "Список пуст");
    }

    @Test
    void clearAllTasks() {
        inMemoryTaskManager.clearAllTasks();
        List<Task> tasks = inMemoryTaskManager.printAllSimpleTasks();
        assertTrue(tasks.isEmpty(), "Список пуст");
    }

    @Test
    void clearAllEpics() {
        inMemoryTaskManager.clearAllEpics();
        List<Epic> epics = inMemoryTaskManager.printAllEpics();
        assertTrue(epics.isEmpty(), "Список пуст");
    }

    @Test
    void clearAllSubTasks() {
        inMemoryTaskManager.clearAllSubTasks();
        List<SubTask> subTasks = inMemoryTaskManager.printAllSubTasks();
        assertTrue(subTasks.isEmpty(), "Список пуст");
    }
}