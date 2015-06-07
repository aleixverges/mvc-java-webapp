package com.shibsted.mvc.controller;

import com.shibsted.mvc.view.View;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

public class PageController {

    private View view;
    private HttpExchange httpExchange;

    public PageController(View view, HttpExchange httpExchange) {
        this.view = view;
        this.httpExchange = httpExchange;
    }

    public void loginAction() {
        this.render("login");
    }

    public void pageAction() {
        this.render("page1");
    }

    private void render(String template) {
        OutputStream os = this.httpExchange.getResponseBody();
        this.view.setOutputStream(os);

        try {
            this.view.render(template);
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }
}
