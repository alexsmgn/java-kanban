package server;

import com.google.gson.Gson;
import manager.InMemoryTaskManager;
import manager.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import server.tokens.TaskToken;
import status.Status;
import tasks.Epic;
import tasks.SimpleTask;
import tasks.SubTask;
import tasks.Task;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HistoryTests {


    // создаём экземпляр InMemoryTaskManager
    TaskManager manager = new InMemoryTaskManager();
    // передаём его в качестве аргумента в конструктор HttpTaskServer
    HttpTaskServer taskServer = new HttpTaskServer(manager);
    Gson gson = taskServer.getGson();

    public HistoryTests() throws IOException {
    }

    @BeforeEach
    public void setUp() {
        manager.clearAllTasks();
        manager.clearAllSubTasks();
        manager.clearAllEpics();
        taskServer.start();
    }

    @AfterEach
    public void shutDown() {
        taskServer.stop();
    }

    @Test
    void getHistory() throws IOException, InterruptedException {
        SimpleTask simpleTask = new SimpleTask(1, "title", Status.NEW, "target", LocalDateTime.now(),
                Duration.ofMinutes(15));
        Epic epic = new Epic(1, "title", Status.NEW, "target",
                LocalDateTime.of(2024, 8, 28, 20, 0),
                LocalDateTime.of(2024, 8, 28, 22, 0),
                Duration.ofHours(2));
        SubTask subTask = new SubTask(4, "title", Status.NEW, "target",
                LocalDateTime.of(2024, 8, 28, 20, 0),
                Duration.ofMinutes(15), 2);

        int idSimple = manager.addTask(simpleTask);
        int idEpic = manager.addEpic(epic);
        int idSub = manager.addSubTask(subTask);

        manager.printSimpleTaskById(idSimple);
        manager.printEpicById(idEpic);
        manager.printSubTaskById(idSub);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/history");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        List<Task> history = gson.fromJson(response.body(), new TaskToken().getType());

        assertEquals(200, response.statusCode());
        assertEquals(3, history.size());
        assertEquals(idEpic, history.get(1).getId());
        assertEquals(idSimple, history.get(0).getId());
        assertEquals(idSub, history.get(2).getId());
    }
}
