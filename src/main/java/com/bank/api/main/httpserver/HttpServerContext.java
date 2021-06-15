package com.bank.api.main.httpserver;

import com.sun.net.httpserver.HttpHandler;

public class HttpServerContext {
    private String uri;
    private HttpHandler httpHandler;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public HttpHandler getHttpHandler() {
        return httpHandler;
    }

    public void setHttpHandler(HttpHandler httpHandler) {
        this.httpHandler = httpHandler;
    }
}
