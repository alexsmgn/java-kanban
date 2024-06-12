import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {


    private int nextId = 1;
    HashMap<Integer, SubTask> subTasks = new HashMap<>();
    HashMap<Integer, Epic> epics = new HashMap<>();
    HashMap<Integer, SimpleTask> simpleTasks = new HashMap<>();

    public int getNextId() {
        return nextId;
    }

    public void addSubTask(SubTask subTask) {
        subTask.setId(nextId);
        nextId++;
        Epic epic = epics.get(subTask.getEpicId());
        if (epic != null) {
            subTasks.put(subTask.getId(), subTask);
            epic.setSubTaskIds(nextId);
            //updateEpicStatus(epic); с этим методом подзадача не добавляется
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

        for (int i = 0; i < epic.getSubTaskIds().size(); i++) {
            epicSubtasks.add(subTasks.get(epic.getSubTaskIds().get(i)));
        }
            for (SubTask subTask : epicSubtasks) {
                if (subTask.getStatus() == Task.Status.NEW) {
                    countNew++;
                }
                if (subTask.getStatus() == Task.Status.DONE) {
                    countDone++;
                } else {
                    epic.setStatus(Task.Status.IN_PROGRESS);
                    return;
                }

                if (countNew == epicSubtasks.size()) {
                    epic.setStatus(Task.Status.NEW);
                } else if (countDone == epicSubtasks.size()) {
                    epic.setStatus(Task.Status.DONE);
                } else {
                    epic.setStatus(Task.Status.IN_PROGRESS);
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
        if (epic == null) {
            return;
        }
        subTasks.replace(newSubTask.getId(), newSubTask);
        updateEpicStatus(epic);
    }

    public void updateEpic (Epic epic) {
        if (epics.containsKey(epic.getId())) {
            epics.replace(epic.getId(), epic);
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
            //epic.getSubTaskIds().remove(nextId);
            updateEpicStatus(epic);
            subTasks.remove(nextId);
        }
    }

    public void tasksDel() {
        simpleTasks.clear();
    }

    public void clearAllSimpleTasks() {
        simpleTasks.clear();
    }

    public void clearAllEpics() {
        epics.clear();
        subTasks.clear();
    }

    public void clearAllSubTasks(Epic epic) {
        subTasks.remove(epic.getId());
        updateEpicStatus(epic);
    }

/*
    public void printAllSimpleTasks() {
        System.out.println(simpleTasks);
    }

    public void printAllEpics() {
        System.out.println(epics);
    }

    public void printAllSubTasks() {
        System.out.println(subTasks);
    }

 */

    public ArrayList<Task> printAllSimpleTasks() {
        return new ArrayList<>(simpleTasks.values());
    }

    public ArrayList<Epic> printAllEpics() {
        return new ArrayList<>(epics.values());
    }

    public ArrayList<SubTask> printAllSubTasks() {
        return new ArrayList<>(subTasks.values());
    }
}






