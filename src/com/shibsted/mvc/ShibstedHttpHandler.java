package com.shibsted.mvc;

import com.shibsted.mvc.view.View;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

public class ShibstedHttpHandler implements HttpHandler {

    private View view;

    public ShibstedHttpHandler(View view) {
        this.view = view;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        URI uri = httpExchange.getRequestURI();
        httpExchange.sendResponseHeaders(200, 0);
        OutputStream os = httpExchange.getResponseBody();

        this.view.setOutputStream(os);
        this.view.render(uri.getPath());
    }
}
