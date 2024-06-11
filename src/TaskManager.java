import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    int nextId = 1;
    HashMap<Integer, SubTask> subTasks = new HashMap<>();
    HashMap<Integer, Epic> epics = new HashMap<>();
    HashMap<Integer, SimpleTask> simpleTasks = new HashMap<>();

    public void addSubTask(SubTask subTask) {
        subTask.setId(nextId);
        nextId++;
        subTasks.put(subTask.getId(), subTask);
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

    private void updateEpicStatus(Epic epic) {
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
            if (subTask.getStatus() == Task.Status.DONE) {
                countDone++;
            }
            if (subTask.getStatus() == Task.Status.NEW) {
                countNew++;
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

    public void update(SimpleTask simpleTask) {
        simpleTasks.put(simpleTask.getId(), simpleTask);

    }

    public void update(SubTask subTask) {
        subTasks.put(subTask.getId(), subTask);
        updateEpicStatus(epics.get(subTask.epicId));
    }

    public void update(Epic epic) {
        epics.put(epic.getId(), epic);
        updateEpicStatus(epic);
    }

    public void delEpicById(int nextId) {
        Epic epic = epics.get(nextId);
        if (epic != null) {
            for (Integer taskId : epic.getSubTaskIds()) {
                subTasks.remove(taskId);
            }
            epic.getSubTaskIds().clear();
        }
        epics.remove(nextId);
    }

    public void delSubTaskById(int nextId) {
        SubTask subTask = subTasks.get(nextId);
        if(subTask != null) {
            Epic epic = epics.get(subTask.getEpicId());
            epic.getSubTaskIds().remove(nextId);
            updateEpicStatus(epic);
            subTasks.remove(nextId);
        }
    }

    public void tasksDel() {
        simpleTasks.clear();
        }
    }






