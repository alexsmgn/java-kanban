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


public class FileBackedTaskManager extends InMemoryTaskManager {

    private final File file;

    public FileBackedTaskManager(File file) {
        this.file = file;
    }

    protected static Task fromString(String value) {
        String[] arrays = value.split(",");
        int id = Integer.parseInt(arrays[0]);
        String type = arrays[1];
        String name = arrays[2];
        Status status = Status.valueOf(arrays[3]);
        String description = arrays[4];

        if (type.equals("EPIC")) {
            return new Epic(id, name, status, description);
        } else if (type.equals("SUBTASK")) {
            int epicId = Integer.parseInt(arrays[5]);
            return new SubTask(id, name, status, description, epicId);
        } else {
            return new SimpleTask(id, name, status, description);
        }
    }

    private static String getIdFromEpic(Task task) {
        if (task instanceof SubTask) {
            return Integer.toString(((SubTask) task).getEpicId());
        }
        return "";
    }

    protected static String toString(Task task) {
        return task.getId() + "," +
                task.getType() + "," +
                task.getTitle() + "," +
                task.getStatus() + "," +
                task.getTarget() + "," +
                getIdFromEpic(task);
    }

    private void save() {

        try {
            if (Files.exists(file.toPath())) {
                Files.delete(file.toPath());
            }
            Files.createFile(file.toPath());
        } catch (IOException e) {
            throw new ManagerSaveException("Файл для сохранения отсутствует");
        }

        try (FileWriter fileWriter = new FileWriter(file, StandardCharsets.UTF_8)) {
            fileWriter.write("id,type,name,status,description,epic\n");
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
                } else {
                    fileLoader.simpleTasks.put(task.getId(), (SimpleTask) task);
                }
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Не удалось загрузить файл");
        }
        return fileLoader;
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

}
