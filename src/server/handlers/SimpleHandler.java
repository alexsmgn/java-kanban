package server.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import exeptions.ManagerSaveException;
import manager.TaskManager;

import java.io.IOException;

public class SimpleHandler extends BaseHttpHandler {

    public SimpleHandler(TaskManager taskManager, Gson gson) {
        super(taskManager, gson);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Endpoints endpoints = getEndpoint(exchange);
        String[] path = exchange.getRequestURI().getPath().split("/");

        try {
            switch (endpoints) {
                case GET:
                    sendText(exchange, gson.toJson(taskManager.printAllSimpleTasks()));
                    break;
                case GET_BY_ID:
                    sendText(exchange, gson.toJson(taskManager.printSimpleTaskById(Integer.parseInt(path[2]))));
                    break;
                case POST:
                    simpleTask = gson.fromJson(taskFromRequestBody(exchange), tasks.SimpleTask.class);
                    taskManager.addTask(simpleTask);
                    writeResponse(exchange, 200, "Задача под номером " + simpleTask.getId() +
                            " успешно добавлена");
                    break;
                case POST_BY_ID:
                    simpleTask = gson.fromJson(taskFromRequestBody(exchange), tasks.SimpleTask.class);
                    taskManager.updateSimpleTask(simpleTask);
                    sendText(exchange, "");
                    break;
                case DELETE_BY_ID:
                    simpleTask = taskManager.printSimpleTaskById(Integer.parseInt(path[2]));
                    taskManager.delSimpleTaskById(simpleTask.getId());
                    writeResponse(exchange, 500, "Задача удалена");
                    break;
                case UNKNOWN:
                    sendNotFount(exchange);
                    break;
            }
        } catch (NullPointerException e) {
            sendNotFount(exchange);
        } catch (ManagerSaveException e) {
            sendHasInteractions(exchange);
        } catch (Exception e) {
            writeResponse(exchange, 500, "");
        }
    }
}
