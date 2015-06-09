package com.shibsted.mvc.controller;

import com.shibsted.mvc.model.UserRepository;
import com.shibsted.mvc.view.View;
import com.sun.net.httpserver.HttpExchange;

public class PageControllerFactory {

    public PageController build(HttpExchange httpExchange) {
        return new PageController(new View(), httpExchange, new UserRepository());
    }
}
