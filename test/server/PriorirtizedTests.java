package server;

import com.google.gson.Gson;
import manager.InMemoryTaskManager;
import manager.TaskManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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

import static org.junit.jupiter.api.Assertions.*;

class PriorirtizedTests {

    // создаём экземпляр InMemoryTaskManager
    TaskManager manager = new InMemoryTaskManager();
    // передаём его в качестве аргумента в конструктор HttpTaskServer
    HttpTaskServer taskServer = new HttpTaskServer(manager);
    Gson gson = taskServer.getGson();

    public PriorirtizedTests() throws IOException {
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
    void getPrioritized() throws IOException, InterruptedException {
        SimpleTask task1 = new SimpleTask(1, "title1", Status.NEW, "target1",
                LocalDateTime.of(2024, 8, 28, 20, 0),
                Duration.ofMinutes(15));
        SimpleTask task2 = new SimpleTask(2, "title2", Status.NEW, "target2",
                LocalDateTime.of(2024, 8, 26, 20, 0),
                Duration.ofMinutes(15));
        SimpleTask task3 = new SimpleTask(3, "title3", Status.NEW, "target3",
                LocalDateTime.of(2024, 8, 27, 20, 0),
                Duration.ofMinutes(15));

        manager.addTask(task1);
        manager.addTask(task2);
        manager.addTask(task3);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/prioritized");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        List<Task> prioritizedHttp = gson.fromJson(response.body(), new TaskToken().getType());

        assertEquals(task2, prioritizedHttp.get(0));
        assertEquals(task3, prioritizedHttp.get(1));
        assertEquals(task1, prioritizedHttp.get(2));
    }
}
