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
        HashMap cookiesHash = getCookies();
        String username = (String) cookiesHash.get("user");

        Headers responseHeaders = this.httpExchange.getResponseHeaders();
        List<String> values = new ArrayList<>();
        values.add("user=; HttpOnly");
        values.add(username + "=; HttpOnly");
        responseHeaders.put("Set-Cookie", values);

        this.render("login", 200);
    }

    public void pageAction() {
        HashMap cookiesHash = getCookies();

        if (cookiesHash.isEmpty()) {
            this.render("forbidden", 403);
            return;
        }

        String sessionId = (String) cookiesHash.get("user");
        String currentRole = (String) cookiesHash.get(sessionId);

        URI uri = this.httpExchange.getRequestURI();
        String uriPath = uri.getPath().replace("/", "");

        if (!uriPath.equals(currentRole)) {
            this.render("forbidden", 403);
            return;
        }

        this.render(uriPath, 200);
    }

    private HashMap getCookies() {
        Headers reqHeaders = this.httpExchange.getRequestHeaders();
        List<String> cookies = reqHeaders.get("Cookie");

        StringTokenizer tokenizer = new StringTokenizer(cookies.get(0), ";");
        HashMap cookiesHash = new HashMap();

        if (cookies.isEmpty()) {
            return cookiesHash;
        }

        for (String cookie : cookies) {
            if (cookie.contains("user")) {
                while (tokenizer.hasMoreTokens()) {
                    String token = tokenizer.nextToken();
                    StringTokenizer cookieTokenizer = new StringTokenizer(token, "=");
                    cookiesHash.put(cookieTokenizer.nextToken().trim(), cookieTokenizer.nextToken().trim());
                }
            }
        }
        return cookiesHash;
    }

    private void startSession(User user) {
        Headers responseHeaders = this.httpExchange.getResponseHeaders();
        List<String> values = new ArrayList<>();
        values.add("user=" + user.getUsername() + "; HttpOnly");
        values.add(user.getUsername() + "=" + user.getRole() + "; HttpOnly");
        responseHeaders.put("Set-Cookie", values);
    }

    private void render(String template, int statusCode) {
        OutputStream os = this.httpExchange.getResponseBody();
        this.view.setOutputStream(os);

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
