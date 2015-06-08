package com.shibsted.mvc.http;

import com.shibsted.mvc.controller.PageController;
import com.shibsted.mvc.model.UserRepository;
import com.shibsted.mvc.view.View;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

public class Handler implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        URI uri = httpExchange.getRequestURI();
        String uriPath = uri.getPath();
        Map params = (Map)httpExchange.getAttribute("parameters");
        PageController pageController = new PageController(new View(), httpExchange, new UserRepository());

        switch (uriPath) {
            case "/page1":
            case "/page2":
            case "/page3":
                pageController.pageAction();
                break;
            case "/logout":
                pageController.logoutAction();
                break;
            default:
                pageController.loginAction(params);
                break;
        }
    }
}
