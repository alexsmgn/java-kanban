package manager;

import exeptions.ManagerSaveException;
import tasks.*;
import status.Status;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class InMemoryTaskManager implements TaskManager {

    protected int nextId;
    protected final Map<Integer, SubTask> subTasks;
    protected final Map<Integer, Epic> epics;
    protected final Map<Integer, SimpleTask> simpleTasks;
    protected final Set<Task> prioritized;

    private final HistoryManager historyManager;

    public InMemoryTaskManager() {
        historyManager = new InMemoryHistoryManager();
        nextId = 0;
        subTasks = new HashMap<>();
        epics = new HashMap<>();
        simpleTasks = new HashMap<>();
        prioritized = new TreeSet<>(Comparator.comparing(Task::getStartTime));
    }


    @Override
    public int addTask(SimpleTask simpleTask) {
        nextId++;
        simpleTask.setId(nextId);
        simpleTasks.put(simpleTask.getId(), simpleTask);
        validateTask(simpleTask);
        addPrioritized(simpleTask);
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
            validateTask(subTask);
            addPrioritized(subTask);
            subTasks.put(subTask.getId(), subTask);
            epic.setSubTaskIds(nextId);
            updateEpicStatus(epic);
            updateEpicTime(epic);
        }
        return subTask.getId();
    }

    @Override
    public void updateSimpleTask(SimpleTask simpleTask) {
        if (simpleTasks.containsKey(simpleTask.getId())) {
            simpleTasks.put(simpleTask.getId(), simpleTask);
            validateTask(simpleTask);
            addPrioritized(simpleTask);
        }
    }

    @Override
    public void updateSubTask(SubTask newSubTask) {
        if (newSubTask == null || !subTasks.containsKey(newSubTask.getId())) {
            return;
        }
        Epic epic = epics.get(newSubTask.getEpicId());
        if (newSubTask.getEpicId() != subTasks.get(newSubTask.getId()).getEpicId()) {
            return;
        }
        subTasks.replace(newSubTask.getId(), newSubTask);
        updateEpicStatus(epic);
        updateEpicTime(epic);
        validateTask(newSubTask);
        addPrioritized(newSubTask);
    }

    @Override
    public void updateEpic(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            String newTitle = epic.getTitle();
            String newTarget = epic.getTarget();
            epics.get(epic.getId()).setTitle(newTitle);
            epics.get(epic.getId()).setTarget(newTarget);
            updateEpicTime(epic);
        }
    }

    @Override
    public void delSimpleTaskById(int nextId) {
        simpleTasks.remove(nextId);
        historyManager.remove(nextId);
    }

    @Override
    public void delEpicById(int nextId) {
        Epic epic = epics.get(nextId);
        if (epic != null) {
            for (Integer taskId : epic.getSubTaskIds()) {
                prioritized.remove(subTasks.get(taskId));
                subTasks.remove(taskId);
                historyManager.remove(taskId);
            }
            epics.remove(nextId);
            historyManager.remove(nextId);
        }
    }

    @Override
    public void delSubTaskById(int nextId) {
        SubTask subTask = subTasks.get(nextId);
        if (subTask != null) {
            Epic epic = epics.get(subTask.getEpicId());
            epic.remSubtask(subTask);
            updateEpicStatus(epic);
            updateEpicTime(epic);
            prioritized.remove(subTask);
            subTasks.remove(nextId);
            historyManager.remove(nextId);
        }
    }

    @Override
    public void clearAllTasks() {
        for (Integer id : simpleTasks.keySet()) {
            historyManager.remove(id);
        }
        simpleTasks.clear();
    }

    @Override
    public void clearAllEpics() {
        for (Integer id : subTasks.keySet()) {
            historyManager.remove(id);
        }
        for (Integer id : epics.keySet()) {
            historyManager.remove(id);
        }
        epics.clear();
        subTasks.clear();
    }

    @Override
    public void clearAllSubTasks() {
        subTasks.clear();
        for (Epic epic : epics.values()) {
            for (int subTaskId : epic.getSubTaskIds()) {
                historyManager.remove(subTaskId);
            }
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

    private void updateEpicTime(Epic epic) {
        List<Task> subTaskList = getPrioritizedTasks().stream()
                .filter(task -> task.getType().equals(Tasks.SUBTASK))
                .filter(task -> ((SubTask) task).getEpicId() == epic.getId())
                .toList();
        if (subTaskList.isEmpty()) {
            return;
        }

        Duration duration = Duration.ofHours(0);
        for (Task subTask : subTaskList) {
            duration = duration.plus(subTask.getDuration());
        }

        LocalDateTime startTime = subTaskList.getFirst().getStartTime();
        LocalDateTime endTime = subTaskList.getLast().getEndTime();

        epic.setStartTime(startTime);
        epic.setEndTime(endTime);
        epic.setDuration(duration);
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
            for (Integer subTaskId : epic.getSubTaskIds()) {
                subTasksNew.add(subTasks.get(subTaskId));
            }
        }
        return subTasksNew;
    }

    @Override
    public List<Task> getHistory() {
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

    @Override
    public List<Task> getPrioritizedTasks() {
        return new ArrayList<>(prioritized);
    }

    protected void addPrioritized(Task task) {
        if (task.getType().equals(Tasks.EPIC)) return;
        List<Task> taskList = getPrioritizedTasks();
        if (task.getStartTime() != null && task.getEndTime() != null) {
            for (Task task1 : taskList) {
                if (task1.getId() == task.getId()) prioritized.remove(task1); //удаление одинаковых задач
                if (checkTimeOverlay(task, task1)) {
                    return;
                }
            }
            prioritized.add(task);
        }
    }

    private boolean checkTimeOverlay(Task task1, Task task2) {
        return !task1.getEndTime().isBefore(task2.getStartTime()) &&
                !task1.getStartTime().isAfter(task2.getEndTime());
    }

    private void validateTask(Task task) {
        if (task == null || task.getStartTime() == null) return;
        List<Task> taskList = getPrioritizedTasks();
        for (Task listTask : taskList) {
            if (listTask == task) {
                continue;
            }
            boolean taskOverlay = checkTimeOverlay(task, listTask);
            if (taskOverlay) {
                throw new ManagerSaveException("Задачи " + task.getId() + " и " + listTask.getId() + " пересекаются");
            }
        }
    }

}






