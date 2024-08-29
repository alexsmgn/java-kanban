package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import manager.TaskManager;
import server.adapters.DurationAdapter;
import server.adapters.LocalDataTimeAdapter;
import server.handlers.*;
import tasks.Epic;
import tasks.SimpleTask;
import tasks.SubTask;
import tasks.Task;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class HttpTaskServer {

    protected Gson gson;
    protected TaskManager taskManager;
    protected HttpServer httpServer;
    protected static final int PORT = 8080;

    public HttpTaskServer(TaskManager taskManager) throws IOException {
        this.taskManager = taskManager;
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDataTimeAdapter())
                .registerTypeAdapter(Duration.class, new DurationAdapter())
                .create();
        try {
            httpServer = HttpServer.create(new InetSocketAddress(PORT), 0);
            httpServer.createContext("/tasks", new SimpleHandler(taskManager, gson));
            httpServer.createContext("/epics", new EpicHandler(taskManager, gson));
            httpServer.createContext("/subtasks", new SubHandler(taskManager, gson));
            httpServer.createContext("/history", new HistoryHandler(taskManager, gson));
            httpServer.createContext("/prioritized", new PrioritizedHandler(taskManager, gson));
        } catch (IOException e) {
            throw new RuntimeException("Ошибка запуска сервера на порте" + PORT);
        }
    }

    public Gson getGson() {
        return gson;
    }

    public void start() {
        System.out.println("Сервер запущен на порте " + PORT);
        System.out.println("http://localhost:" + PORT + "/");
        httpServer.start();
    }

    public void stop() {
        System.out.println("Сервер остановлен");
        httpServer.stop(0);
    }


}
