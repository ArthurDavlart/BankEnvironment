package com.bank.api.main.httpserver;

import com.bank.api.main.httpserver.controllers.HttpServerUserController;
import com.bank.api.main.httpserver.httpseverhandlers.UserHttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Application {
    public static void main(String[] args) throws IOException {
//        // TODO можно вынести в отдельный класс, что организовать чтение из конфига
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        HttpServerUserController httpServerUserController = new HttpServerUserController();
        // Нужно продумать процесс создания хандлеров путем чтения контроллеров
         server.createContext("/users", new UserHttpHandler(httpServerUserController));


        server.setExecutor(null); // creates a default executor

        server.start();

    }
}
