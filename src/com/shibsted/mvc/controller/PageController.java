package com.shibsted.mvc.controller;

import com.shibsted.mvc.ShibstedHttpHandler;
import com.shibsted.mvc.view.View;
import com.sun.net.httpserver.HttpServer;

public class PageController {

    private View view;

    public PageController(View view) {
        this.view = view;
    }

    public void init(HttpServer server)
    {
        server.createContext("/page1", new ShibstedHttpHandler(this.view));
        server.createContext("/page2", new ShibstedHttpHandler(this.view));
        server.createContext("/page3", new ShibstedHttpHandler(this.view));
    }
}
