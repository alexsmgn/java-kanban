package manager;

import tasks.Epic;
import tasks.SimpleTask;
import tasks.SubTask;
import tasks.Task;

import java.util.List;

public interface TaskManager {

    int addTask(SimpleTask simpleTask);

    int addEpic(Epic epic);

    int addSubTask(SubTask subTask);

    void updateSimpleTask(SimpleTask simpleTask);

    void updateSubTask(SubTask newSubTask);

    void updateEpic(Epic epic);

    void delSimpleTaskById(int nextId);

    void delEpicById(int nextId);

    void delSubTaskById(int nextId);

    void clearAllTasks();

    void clearAllEpics();

    void clearAllSubTasks();

    void updateEpicStatus(Epic epic);

    List<Task> printAllSimpleTasks();

    List<Epic> printAllEpics();

    List<SubTask> printAllSubTasks();

    SimpleTask printSimpleTaskById(int nextId);

    Epic printEpicById(int nextId);

    SubTask printSubTaskById(int nextId);

    List<SubTask> printSubTasksByEpicId(int nextId);

    List<Task> getHistory();

    List<Task> getPrioritizedTasks();

}
