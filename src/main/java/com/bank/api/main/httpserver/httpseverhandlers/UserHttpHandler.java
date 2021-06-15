package com.bank.api.main.httpserver.httpseverhandlers;

import com.bank.api.main.httpserver.controllers.HttpServerUserController;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class UserHttpHandler implements HttpHandler {
    private String justNumber = "\\d+";
    private final HttpServerUserController userController;

    public UserHttpHandler(HttpServerUserController userController) {
        this.userController = userController;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String[] pathElements = path.split("/");

        if (pathElements.length == 2){
            getAll(exchange);
            return;
        }

        if (!pathElements[2].isEmpty()
                && pathElements[2].matches(justNumber)
                && pathElements.length == 3){
            get(exchange, Long.parseLong(pathElements[2]));
            return;
        }

        set400Response(exchange);
    }

    private void getAll(HttpExchange exchange) throws IOException{
        String response = "response";
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private void get(HttpExchange exchange, long id) throws IOException {
        String response = "response_" + id;
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private void set400Response(HttpExchange exchange) throws IOException {
        String response = "Bad request!";
        exchange.sendResponseHeaders(400, response.length());

        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
