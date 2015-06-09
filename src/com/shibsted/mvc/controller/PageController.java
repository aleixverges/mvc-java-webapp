package com.shibsted.mvc.controller;

import com.shibsted.mvc.model.User;
import com.shibsted.mvc.model.UserRepository;
import com.shibsted.mvc.view.View;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.*;

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

        if (user != null) {
            startSession(user);
            this.render("logged", 200);
            return;
        }

        this.render("login", 200);
    }

    public void logoutAction() {
        endSession();
        this.render("login", 200);
        return;
    }

    public void pageAction(Map cookies) {
        if (cookies.isEmpty()) {
            this.render("forbidden", 403);
            return;
        }

        URI uri = this.httpExchange.getRequestURI();
        String uriPath = uri.getPath().replace("/", "");
        String currentRole = (String) cookies.get("role");

        if (!uriPath.equals(currentRole)) {
            this.render("forbidden", 403);
            return;
        }

        this.render(uriPath, 200);
    }

    private void startSession(User user) {
        Headers responseHeaders = this.httpExchange.getResponseHeaders();
        List<String> values = new ArrayList<>();
        values.add("user=" + user.getUsername() + "; HttpOnly");
        values.add("role=" + user.getRole() + "; HttpOnly");
        responseHeaders.put("Set-Cookie", values);
    }

    private void endSession() {
        Headers responseHeaders = this.httpExchange.getResponseHeaders();
        List<String> values = new ArrayList<>();
        values.add("user=; HttpOnly");
        values.add("role=; HttpOnly");
        responseHeaders.put("Set-Cookie", values);
    }

    private void render(String template, int statusCode) {
        try {
            this.sendResponse(statusCode);
            this.view.render(template);
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void sendResponse(int statusCode) {
        try {
            this.httpExchange.sendResponseHeaders(statusCode, 0);
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }
}
