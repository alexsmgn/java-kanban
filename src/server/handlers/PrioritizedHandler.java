package server.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import exeptions.ManagerSaveException;
import manager.TaskManager;

import java.io.IOException;

public class PrioritizedHandler extends BaseHttpHandler {

    public PrioritizedHandler(TaskManager taskManager, Gson gson) {
        super(taskManager, gson);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Endpoints endpoints = getEndpoint(exchange);

        try {
            switch (endpoints) {
                case GET:
                    sendText(exchange, gson.toJson(taskManager.getPrioritizedTasks()));
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
