package server.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import exeptions.ManagerSaveException;
import manager.TaskManager;

import java.io.IOException;

public class SubHandler extends BaseHttpHandler {

    public SubHandler(TaskManager taskManager, Gson gson) {
        super(taskManager, gson);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Endpoints endpoints = getEndpoint(exchange);
        String[] path = exchange.getRequestURI().getPath().split("/");

        try {
            switch (endpoints) {
                case GET:
                    sendText(exchange, gson.toJson(taskManager.printAllSubTasks()));
                    break;
                case GET_BY_ID:
                    sendText(exchange, gson.toJson(taskManager.printSubTaskById(Integer.parseInt(path[2]))));
                    break;
                case POST:
                    subTask = gson.fromJson(taskFromRequestBody(exchange), tasks.SubTask.class);
                    taskManager.addSubTask(subTask);
                    writeResponse(exchange, 200, "Задача под номером " + subTask.getId() +
                            " успешно добавлена");
                    break;
                case POST_BY_ID:
                    subTask = gson.fromJson(taskFromRequestBody(exchange), tasks.SubTask.class);
                    taskManager.updateSubTask(subTask);
                    sendText(exchange, "");
                    break;
                case DELETE_BY_ID:
                    subTask = taskManager.printSubTaskById(Integer.parseInt(path[2]));
                    taskManager.delSubTaskById(subTask.getId());
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
