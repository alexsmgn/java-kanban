package manager;

import tasks.Task;
import tasks.SimpleTask;
import tasks.Epic;
import tasks.SubTask;
import status.Status;
import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {

    private int nextId = 0;
    private final HashMap<Integer, SubTask> subTasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, SimpleTask> simpleTasks = new HashMap<>();

    public int getNextId() {
        return nextId;
    }

    public void addTask(SimpleTask simpleTask) {
        nextId++;
        simpleTask.setId(nextId);

        simpleTasks.put(simpleTask.getId(), simpleTask);
    }

    public void addEpic(Epic epic) {
        nextId++;
        epic.setId(nextId);

        epics.put(epic.getId(), epic);
    }

    public void addSubTask(SubTask subTask) {
        nextId++;
        subTask.setId(nextId);
        Epic epic = epics.get(subTask.getEpicId());
        if (epic != null) {
            subTasks.put(subTask.getId(), subTask);
            epic.setSubTaskIds(nextId);
            updateEpicStatus(epic);
        }
    }



    public void updateSimpleTask (SimpleTask simpleTask) {
        if (simpleTasks.containsKey(simpleTask.getId())) {
            simpleTasks.put(simpleTask.getId(), simpleTask);
        }
    }

    public void updateSubTask (SubTask newSubTask) {
        if (newSubTask == null || !subTasks.containsKey(newSubTask.getId())) {
            return;
        }
        Epic epic = epics.get(newSubTask.getEpicId());
        if (epic == null || !epics.containsKey(epic.getId())) {
            return;
        }
        subTasks.replace(newSubTask.getId(), newSubTask);
        updateEpicStatus(epic);
    }

    public void updateEpic (Epic epic) {

        if (epics.containsKey(epic.getId())) {
            Epic newEpic = new Epic(epic.getTitle(), epic.getTarget());
            epics.remove(epic.getId(), epic);
            epics.put(newEpic.getId(), newEpic);
            updateEpicStatus(epic);
        }
    }

    public void delSimpleTaskById (int nextId) {
        simpleTasks.remove(nextId);
    }

    public void delEpicById(int nextId) {
        Epic epic = epics.get(nextId);
        if (epic != null) {
            for (Integer taskId : epic.getSubTaskIds()) {
                subTasks.remove(taskId);
                epics.remove(nextId);
            }
        }
    }

    public void delSubTaskById(int nextId) {
        SubTask subTask = subTasks.get(nextId);
        if(subTask != null) {
            Epic epic = epics.get(subTask.getEpicId());
            epic.remSubtask(subTask);
            updateEpicStatus(epic);
            subTasks.remove(nextId);
        }
    }

    public void clearAllTasks() {
        simpleTasks.clear();
    }


    public void clearAllEpics() {
        epics.clear();
        subTasks.clear();
    }

    public void clearAllSubTasks() {
        subTasks.clear();
        for(Epic epic: epics.values()) {
            epic.getSubTaskIds().clear();
            updateEpicStatus(epic);
        }
    }

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
            }else if (subTask.getStatus() == Status.NEW) {
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

    public ArrayList<Task> printAllSimpleTasks() {
        return new ArrayList<>(simpleTasks.values());
    }

    public ArrayList<Epic> printAllEpics() {
        return new ArrayList<>(epics.values());
    }

    public ArrayList<SubTask> printAllSubTasks() {
        return new ArrayList<>(subTasks.values());
    }

    public SimpleTask printSimpleTaskById(int nextId) {
        SimpleTask simpleTask = simpleTasks.get(nextId);
        return simpleTask;
    }

    public Epic printEpicById(int nextId) {
        Epic epic = epics.get(nextId);
        return epic;
    }

    public SubTask printSubTaskById(int nextId) {
        SubTask subTask = subTasks.get(nextId);
        return subTask;
    }

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
}






