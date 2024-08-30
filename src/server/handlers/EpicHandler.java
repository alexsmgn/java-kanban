package server.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import exeptions.ManagerSaveException;
import manager.TaskManager;

import java.io.IOException;

public class EpicHandler extends BaseHttpHandler {

    public EpicHandler(TaskManager taskManager, Gson gson) {
        super(taskManager, gson);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Endpoints endpoints = getEndpoint(exchange);
        String[] path = exchange.getRequestURI().getPath().split("/");

        try {
            switch (endpoints) {
                case GET:
                    sendText(exchange, gson.toJson(taskManager.printAllEpics()));
                    break;
                case GET_BY_ID:
                    sendText(exchange, gson.toJson(taskManager.printEpicById(Integer.parseInt(path[2]))));
                    break;
                case GET_SUB_BY_EPIC_ID:
                    sendText(exchange, gson.toJson(taskManager.printSubTasksByEpicId(Integer.parseInt(path[2]))));
                    break;
                case POST:
                    epic = gson.fromJson(taskFromRequestBody(exchange), tasks.Epic.class);
                    taskManager.addEpic(epic);
                    writeResponse(exchange, 200, "Задача под номером " + epic.getId()
                            + " успешно добавлена");
                    break;
                case POST_BY_ID:
                    epic = gson.fromJson(taskFromRequestBody(exchange), tasks.Epic.class);
                    taskManager.updateEpic(epic);
                    sendText(exchange, "");
                    break;
                case DELETE_BY_ID:
                    epic = taskManager.printEpicById(Integer.parseInt(path[2]));
                    taskManager.delEpicById(epic.getId());
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
