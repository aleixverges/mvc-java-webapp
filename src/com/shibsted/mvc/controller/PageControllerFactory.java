package com.shibsted.mvc.controller;

import com.shibsted.mvc.model.UserRepository;
import com.shibsted.mvc.view.TemplateFactory;
import com.shibsted.mvc.view.TemplateReaderFactory;
import com.shibsted.mvc.view.TemplateTokenReplacer;
import com.shibsted.mvc.view.View;
import com.sun.net.httpserver.HttpExchange;

import java.io.OutputStream;

public class PageControllerFactory {

    public PageController build(HttpExchange httpExchange) {
        OutputStream os = httpExchange.getResponseBody();
        View view = new View(os, new TemplateFactory(), new TemplateReaderFactory(), new TemplateTokenReplacer());

        return new PageController(view, httpExchange, new UserRepository());
    }
}
