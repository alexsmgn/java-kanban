package manager;

import status.Status;
import tasks.*;
import exeptions.ManagerSaveException;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileWriter;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class FileBackedTaskManager extends InMemoryTaskManager {

    private final File file;
    private static final String HEADER = "id,type,name,status,description,start,end,duration,epic\n";

    public FileBackedTaskManager(File file) {
        this.file = file;
    }

    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy,HH:mm");
    static DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    static DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");

    private static Task fromString(String value) {
        String[] arrays = value.split(",");
        int id = Integer.parseInt(arrays[0]);
        String type = arrays[1];
        String name = arrays[2];
        Status status = Status.valueOf(arrays[3]);
        String description = arrays[4];
        LocalDateTime startTime = LocalDateTime.of(LocalDate.parse(arrays[5], formatterDate),
                LocalTime.parse(arrays[6], formatterTime));
        Duration duration = Duration.ofMinutes(Long.parseLong(arrays[9]));

        if (type.equals("EPIC")) {
            LocalDateTime epicEndTime = LocalDateTime.of(LocalDate.parse(arrays[7], formatterDate),
                    LocalTime.parse(arrays[8], formatterTime));
            return new Epic(id, name, status, description, startTime, epicEndTime, duration);
        } else if (type.equals("SUBTASK")) {
            int epicId = Integer.parseInt(arrays[10]);
            return new SubTask(id, name, status, description, startTime, duration, epicId);
        } else {
            return new SimpleTask(id, name, status, description, startTime, duration);
        }

    }

    private static String getIdFromEpic(Task task) {
        if (task instanceof SubTask) {
            return Integer.toString(((SubTask) task).getEpicId());
        }
        return "";
    }

    private static String toString(Task task) {

        String startTime = task.getStartTime() != null ? task.getStartTime().format(formatter) : "";
        String endTime = task.getEndTime() != null ? task.getEndTime().format(formatter) : "";
        String duration = task.getDuration() != null ? String.valueOf(task.getDuration().toMinutes()) : "";

        return task.getId() + "," +
                task.getType() + "," +
                task.getTitle() + "," +
                task.getStatus() + "," +
                task.getTarget() + "," +
                startTime + "," +
                endTime + "," +
                duration + "," +
                getIdFromEpic(task);
    }

    public static FileBackedTaskManager loadFromFile(File file) {
        FileBackedTaskManager fileLoader = new FileBackedTaskManager(file);
        try (BufferedReader br = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
            String line = br.readLine();
            while (br.ready()) {
                line = br.readLine();

                Task task = fromString(line);

                if (task.getType().equals(Tasks.EPIC)) {
                    fileLoader.epics.put(task.getId(), (Epic) task);
                } else if (task.getType().equals(Tasks.SUBTASK)) {
                    fileLoader.subTasks.put(task.getId(), (SubTask) task);
                    fileLoader.epics.get(((SubTask) task).getEpicId()).setSubTaskIds(task.getId());
                } else {
                    fileLoader.simpleTasks.put(task.getId(), (SimpleTask) task);
                }
                if (fileLoader.nextId < task.getId()) {
                    fileLoader.nextId = task.getId();
                }
                fileLoader.addPrioritized(task);
            }
        } catch (IOException e) {
             throw new ManagerSaveException("Не удалось загрузить файл");
        }
        return fileLoader;
    }

    private void save() {
        try {
            if (Files.exists(file.toPath())) {
                Files.delete(file.toPath());
            } else {
                Files.createFile(file.toPath());
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Файл для сохранения отсутствует");
        }

        try (FileWriter fileWriter = new FileWriter(file, StandardCharsets.UTF_8)) {
            fileWriter.write(HEADER);
            for (Task simpleTask : printAllSimpleTasks()) {
                fileWriter.write(toString(simpleTask) + "\n");
            }

            for (Epic epic : printAllEpics()) {
                fileWriter.write(toString(epic) + "\n");
            }

            for (SubTask subTask : printAllSubTasks()) {
                fileWriter.write(toString(subTask) + "\n");
            }

        } catch (IOException e) {
            throw new ManagerSaveException("Не удалось сохранить задачи в файл");
        }
    }

    @Override
    public int addTask(SimpleTask simpleTask) {
        super.addTask(simpleTask);
        save();
        return simpleTask.getId();
    }

    @Override
    public int addSubTask(SubTask subTask) {
        super.addSubTask(subTask);
        save();
        return subTask.getId();
    }

    @Override
    public int addEpic(Epic epic) {
        super.addEpic(epic);
        save();
        return epic.getId();
    }

    @Override
    public void delSimpleTaskById(int nextId) {
        super.delSimpleTaskById(nextId);
        save();
    }

    @Override
    public void delEpicById(int nextId) {
        super.delEpicById(nextId);
        save();
    }

    @Override
    public void delSubTaskById(int nextId) {
        super.delSubTaskById(nextId);
        save();
    }

    @Override
    public void clearAllTasks() {
        super.clearAllTasks();
        save();
    }

    @Override
    public void clearAllEpics() {
        super.clearAllEpics();
        save();
    }

    @Override
    public void clearAllSubTasks() {
        super.clearAllSubTasks();
        save();
    }
}
