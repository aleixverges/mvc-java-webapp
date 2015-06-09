package com.shibsted.mvc.http;

import com.shibsted.mvc.controller.PageController;
import com.shibsted.mvc.controller.PageControllerFactory;
import com.shibsted.mvc.model.UserRepository;
import com.shibsted.mvc.view.View;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

public class Handler implements HttpHandler {

    private PageControllerFactory pageControllerFactory;

    public Handler(PageControllerFactory pageControllerFactory) {
        this.pageControllerFactory = pageControllerFactory;
    }

    @Override
    public void handle(HttpExchange httpExchange) {

        URI uri = httpExchange.getRequestURI();
        String uriPath = uri.getPath();
        PageController pageController = this.pageControllerFactory.build(httpExchange);

        switch (uriPath) {
            case "/page1":
            case "/page2":
            case "/page3":
                Map cookies = (Map)httpExchange.getAttribute("cookies");
                pageController.pageAction(cookies);
                break;
            case "/logout":
                pageController.logoutAction();
                break;
            default:
                Map params = (Map)httpExchange.getAttribute("parameters");
                pageController.loginAction(params);
                break;
        }
    }
}
