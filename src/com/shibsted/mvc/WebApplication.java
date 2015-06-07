package com.shibsted.mvc;

import com.shibsted.mvc.controller.PageController;
import com.shibsted.mvc.view.View;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;

public class WebApplication {

    public static void main(String[] args) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(80), 0);
            PageController pageController = new PageController(new View());
            pageController.init(server);
            server.start();
        } catch (java.io.IOException exception) {
            System.out.println(exception.getMessage());
        }
    }
}
