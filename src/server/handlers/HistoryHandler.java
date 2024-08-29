package server.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import exeptions.ManagerSaveException;
import manager.TaskManager;
import server.HttpTaskServer;

import java.io.IOException;

public class HistoryHandler extends BaseHttpHandler {

    public HistoryHandler(TaskManager taskManager, Gson gson) {
        super(taskManager, gson);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Endpoints endpoints = getEndpoint(exchange);
        String[] path = exchange.getRequestURI().getPath().split("/");

        try {
            switch (endpoints) {
                case GET:
                    sendText(exchange, gson.toJson(taskManager.getHistory()));
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
