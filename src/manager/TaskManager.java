package manager;

import tasks.Task;
import tasks.SimpleTask;
import tasks.Epic;
import tasks.SubTask;
import status.Status;
import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {

    private int nextId = 1;
    private HashMap<Integer, SubTask> subTasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, SimpleTask> simpleTasks = new HashMap<>();

    public int getNextId() {
        return nextId;
    }

    public void addSubTask(SubTask subTask) {
        Epic epic = epics.get(subTask.getEpicId());
        if (epic != null) {
        subTask.setId(nextId);
        nextId++;
        subTasks.put(subTask.getId(), subTask);
        epic.setSubTaskIds(nextId);
        updateEpicStatus(epic);
        }

    }

    public void addEpic(Epic epic) {
        epic.setId(nextId);
        nextId++;
        epics.put(epic.getId(), epic);
    }

    public void addTask(SimpleTask simpleTask) {
        simpleTask.setId(nextId);
        nextId++;
        simpleTasks.put(simpleTask.getId(), simpleTask);
    }

    public void updateEpicStatus(Epic epic) {
        if (!epics.containsKey(epic.getId())) {
            return;
        }

        ArrayList<SubTask> epicSubtasks = new ArrayList<>();

        int countNew = 0;
        int countDone = 0;

        for (int i = 0; i<epic.getSubTaskIds().size(); i++) {
            epicSubtasks.add(subTasks.get(epic.getSubTaskIds().get(i)));
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
            Epic newEpic = new Epic(epic.getId(), epic.getTitle(), epic.getTarget());
            epics.put(newEpic.getId(), newEpic);
            updateEpicStatus(epic);
        }
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

    public void tasksDel() {
        simpleTasks.clear();
    }


    public void clearAllEpics() {
        epics.clear();
        subTasks.clear();
    }

    public void clearAllSubTasksFromEpic(Epic epic) {
        subTasks.remove(epic.getId());

        //updateEpicStatus();
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

    public void printSimpleTaskById(int nextId) {
        System.out.println(simpleTasks.get(nextId));
    }

    public void printEpicById(int nextId) {
        System.out.println(epics.get(nextId));
    }

    public void printSubTaskById(int nextId) {
        System.out.println(subTasks.get(nextId));
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






