package server.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import manager.TaskManager;
import tasks.Epic;
import tasks.SimpleTask;
import tasks.SubTask;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public abstract class BaseHttpHandler implements HttpHandler {

    protected TaskManager taskManager;
    protected Gson gson;
    protected SimpleTask simpleTask;
    protected Epic epic;
    protected SubTask subTask;

    protected enum Endpoints {
        GET, GET_BY_ID, GET_SUB_BY_EPIC_ID, POST, POST_BY_ID, DELETE_BY_ID, UNKNOWN
    }

    BaseHttpHandler(TaskManager taskManager, Gson gson) {
        this.taskManager = taskManager;
        this.gson = gson;
    }

    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    protected Endpoints getEndpoint(HttpExchange exchange) {
        String[] partPath = exchange.getRequestURI().getPath().split("/");
        String requestMethod = exchange.getRequestMethod();

        switch (requestMethod) {
            case "GET":
                if (partPath.length == 3) {
                    return Endpoints.GET_BY_ID;
                } else if (partPath.length == 5 && partPath[1].equals("SUB")) {
                    return Endpoints.GET_SUB_BY_EPIC_ID;
                }
                return Endpoints.GET;
            case "POST":
                if (partPath.length == 3) {
                    return Endpoints.POST_BY_ID;
                }
                return Endpoints.POST;
            case "DELETE":
                if (partPath.length == 3) {
                    return Endpoints.DELETE_BY_ID;
                }
            default:
                return Endpoints.UNKNOWN;
        }
    }

    protected void writeResponse(HttpExchange exchange, int responseCode, String responseString) throws IOException {
        exchange.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        try (OutputStream os = exchange.getResponseBody()) {
            exchange.sendResponseHeaders(responseCode, 0);
            os.write(responseString.getBytes(DEFAULT_CHARSET));
        }
        exchange.close();
    }

    protected void sendText(HttpExchange exchange, String text) throws IOException {
        byte[] resp = text.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        exchange.sendResponseHeaders(200, resp.length);
        exchange.getResponseBody().write(resp);
        exchange.close();
    }

    protected void sendNotFount(HttpExchange exchange) throws IOException {
        exchange.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        exchange.sendResponseHeaders(404, 0);
        exchange.close();
    }

    protected void sendHasInteractions(HttpExchange exchange) throws IOException {
        exchange.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        exchange.sendResponseHeaders(406, 0);
        exchange.close();
    }

    protected String taskFromRequestBody(HttpExchange exchange) throws IOException {
        try (InputStream is = exchange.getRequestBody()) {
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            writeResponse(exchange, 500, "");
            throw e;
        }
    }


}

