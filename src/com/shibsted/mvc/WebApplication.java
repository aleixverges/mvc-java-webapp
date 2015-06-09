package com.shibsted.mvc;

import com.shibsted.mvc.controller.PageControllerFactory;
import com.shibsted.mvc.http.CookiesFilter;
import com.shibsted.mvc.http.Handler;
import com.shibsted.mvc.http.ParameterFilter;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;

public class WebApplication {

    public static void main(String[] args) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(80), 0);
            Handler httpHandler = new Handler(new PageControllerFactory());

            HttpContext loginContext = server.createContext("/", httpHandler);
            loginContext.getFilters().add(new ParameterFilter());

            server.createContext("/page1", httpHandler).getFilters().add(new CookiesFilter());
            server.createContext("/page2", httpHandler).getFilters().add(new CookiesFilter());;
            server.createContext("/page3", httpHandler).getFilters().add(new CookiesFilter());;
            server.createContext("/logout", httpHandler).getFilters().add(new CookiesFilter());;
            server.start();

        } catch (java.io.IOException exception) {
            System.out.println(exception.getMessage());
        }
    }
}
