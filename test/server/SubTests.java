package server;

import com.google.gson.Gson;
import manager.InMemoryTaskManager;
import manager.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import status.Status;
import tasks.Epic;
import tasks.SubTask;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SubTests {

    // создаём экземпляр InMemoryTaskManager
    TaskManager manager = new InMemoryTaskManager();
    // передаём его в качестве аргумента в конструктор HttpTaskServer
    HttpTaskServer taskServer = new HttpTaskServer(manager);
    Gson gson = taskServer.getGson();

    public SubTests() throws IOException {
    }

    @BeforeEach
    public void setUp() {
        manager.clearAllTasks();
        manager.clearAllSubTasks();
        manager.clearAllEpics();
        taskServer.start();
        /*
        Epic epic = new Epic(2, "title", Status.NEW, "target",
                LocalDateTime.of(2024, 8, 28, 20, 0),
                LocalDateTime.of(2024, 8, 28, 20, 0), Duration.ofMinutes(15));

         */
        Epic epic = new Epic("title", "target");
        manager.addEpic(epic);
    }

    @AfterEach
    public void shutDown() {
        taskServer.stop();
    }

    @Test
    public void testAddEpic() throws IOException, InterruptedException {

        SubTask subTask = new SubTask(1,"title", Status.NEW, "target",
                LocalDateTime.of(2024,8,28,20,0), Duration.ofMinutes(15), 1);

        String subJson = gson.toJson(subTask);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/subtasks");
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(subJson)).build();


        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());

        List<SubTask> tasksFromManager = manager.printAllSubTasks();

        assertNotNull(tasksFromManager, "Задачи не возвращаются");
        assertEquals(1, tasksFromManager.size(), "Некорректное количество задач");
        assertEquals("title", tasksFromManager.getFirst().getTitle(), "Некорректное имя задачи");
    }
}
