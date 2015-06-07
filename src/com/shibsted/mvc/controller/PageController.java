package com.shibsted.mvc.controller;

import com.shibsted.mvc.model.User;
import com.shibsted.mvc.model.UserRepository;
import com.shibsted.mvc.view.View;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class PageController {

    private View view;
    private HttpExchange httpExchange;
    private UserRepository userRepository;

    public PageController(View view, HttpExchange httpExchange, UserRepository userRepository) {
        this.view = view;
        this.httpExchange = httpExchange;
        this.userRepository = userRepository;
    }

    public void loginAction(Map params) {
        Object username = params.get("username");
        Object password = params.get("password");

        User user = this.userRepository.userOfUsernameAndPassword((String) username, (String) password);

        if (user instanceof User) {

        }

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
