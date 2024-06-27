package manager;

import tasks.Task;
import tasks.SimpleTask;
import tasks.Epic;
import tasks.SubTask;
import status.Status;
import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryTaskManager implements TaskManager {

    private int nextId;
    private final HashMap<Integer, SubTask> subTasks;
    private final HashMap<Integer, Epic> epics;
    private final HashMap<Integer, SimpleTask> simpleTasks;

    private final HistoryManager historyManager;

    public InMemoryTaskManager () {
        historyManager = new InMemoryHistoryManager();
        nextId = 0;
        subTasks = new HashMap<>();
        epics = new HashMap<>();
        simpleTasks = new HashMap<>();
    }

    @Override
    public int addTask(SimpleTask simpleTask) {
        nextId++;
        simpleTask.setId(nextId);
        simpleTasks.put(simpleTask.getId(), simpleTask);
        return simpleTask.getId();
    }

    @Override
    public int addEpic(Epic epic) {
        nextId++;
        epic.setId(nextId);
        epics.put(epic.getId(), epic);
        return epic.getId();
    }

    @Override
    public int addSubTask(SubTask subTask) {
        Epic epic = epics.get(subTask.getEpicId());
        if (epic != null) {
            nextId++;
            subTask.setId(nextId);
            subTasks.put(subTask.getId(), subTask);
            epic.setSubTaskIds(nextId);
            updateEpicStatus(epic);
        }
        return subTask.getId();
    }

    @Override
    public void updateSimpleTask(SimpleTask simpleTask) {
        if (simpleTasks.containsKey(simpleTask.getId())) {
            simpleTasks.put(simpleTask.getId(), simpleTask);
        }
    }

    @Override
    public void updateSubTask(SubTask newSubTask) {
        if (newSubTask == null || !subTasks.containsKey(newSubTask.getId())) {
            return;
        }
        Epic epic = epics.get(newSubTask.getEpicId());
        if (newSubTask.getEpicId()!=subTasks.get(newSubTask.getId()).getEpicId()) {
            return;
        }
        subTasks.replace(newSubTask.getId(), newSubTask);
        updateEpicStatus(epic);
    }

    @Override
    public void updateEpic(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            String newTitle = epic.getTitle();
            String newTarget = epic.getTarget();
            epics.get(epic.getId()).setTitle(newTitle);
            epics.get(epic.getId()).setTarget(newTarget);
        }
    }

    @Override
    public void delSimpleTaskById(int nextId) {
        simpleTasks.remove(nextId);
    }

    @Override
    public void delEpicById(int nextId) {
        Epic epic = epics.get(nextId);
        if (epic != null) {
            for (Integer taskId : epic.getSubTaskIds()) {
                subTasks.remove(taskId);
            }
            epics.remove(nextId);
        }
    }

    @Override
    public void delSubTaskById(int nextId) {
        SubTask subTask = subTasks.get(nextId);
        if(subTask != null) {
            Epic epic = epics.get(subTask.getEpicId());
            epic.remSubtask(subTask);
            updateEpicStatus(epic);
            subTasks.remove(nextId);
        }
    }

    @Override
    public void clearAllTasks() {
        simpleTasks.clear();
    }


    @Override
    public void clearAllEpics() {
        epics.clear();
        subTasks.clear();
    }

    @Override
    public void clearAllSubTasks() {
        subTasks.clear();
        for(Epic epic: epics.values()) {
            epic.remAllSubTasks();
            updateEpicStatus(epic);
        }
    }

    @Override
    public void updateEpicStatus(Epic epic) {
        if (!epics.containsKey(epic.getId())) {
            return;
        }

        ArrayList<SubTask> epicSubtasks = new ArrayList<>();

        int countNew = 0;
        int countDone = 0;

        for (Integer id : epic.getSubTaskIds()) {
            epicSubtasks.add(subTasks.get(id));
        }
        for (SubTask subTask : epicSubtasks) {
            if (subTask.getStatus() == Status.DONE) {
                countDone++;
            } else if (subTask.getStatus() == Status.NEW) {
                countNew++;
            } else {
                epic.setStatus(Status.IN_PROGRESS);
                return;
            }

            if (countNew == epicSubtasks.size()) {
                epic.setStatus(Status.NEW);
            } else if (countDone == epicSubtasks.size()) {
                epic.setStatus(Status.DONE);
            } else {
                epic.setStatus(Status.IN_PROGRESS);
            }
        }
    }

    @Override
    public ArrayList<Task> printAllSimpleTasks() {
        return new ArrayList<>(simpleTasks.values());
    }

    @Override
    public ArrayList<Epic> printAllEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public ArrayList<SubTask> printAllSubTasks() {
        return new ArrayList<>(subTasks.values());
    }

    @Override
    public SimpleTask printSimpleTaskById(int nextId) {
        SimpleTask simpleTask = simpleTasks.get(nextId);
        historyManager.add(simpleTask);
        return simpleTask;
    }

    @Override
    public Epic printEpicById(int nextId) {
        Epic epic = epics.get(nextId);
        historyManager.add(epic);
        return epic;
    }

    @Override
    public SubTask printSubTaskById(int nextId) {
        SubTask subTask = subTasks.get(nextId);
        historyManager.add(subTask);
        return subTask;
    }

    @Override
    public ArrayList<SubTask> printSubTasksByEpicId(int nextId) {
        ArrayList<SubTask> subTasksNew = new ArrayList<>();
        Epic epic = epics.get(nextId);
        if (epic != null) {
            for(Integer subTaskId : epic.getSubTaskIds()) {
                subTasksNew.add(subTasks.get(subTaskId));
            }
        }
        return subTasksNew;
    }

    @Override
    public ArrayList<Task> getHistory() {
        return historyManager.getHistory();
    }

    public void printAllTasks(TaskManager manager) {
        System.out.println("Задачи:");
        for (Task task : manager.printAllSimpleTasks()) {
            System.out.println(task);
        }
        System.out.println("Эпики:");
        for (Task epic : manager.printAllEpics()) {
            System.out.println(epic);

            for (Task task : manager.printSubTasksByEpicId(epic.getId())) {
                System.out.println("--> " + task);
            }
        }
        System.out.println("Подзадачи:");
        for (Task subtask : manager.printAllSubTasks()) {
            System.out.println(subtask);
        }

        System.out.println("История:");
        for (Task task : manager.getHistory()) {
            System.out.println(task);
        }
    }
}






