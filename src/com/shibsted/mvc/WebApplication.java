package com.shibsted.mvc;

import com.shibsted.mvc.http.Handler;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;

public class WebApplication {

    public static void main(String[] args) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(80), 0);
            server.createContext("/", new Handler());
            server.createContext("/page1", new Handler());
            server.createContext("/page2", new Handler());
            server.createContext("/page3", new Handler());
            server.start();
        } catch (java.io.IOException exception) {
            System.out.println(exception.getMessage());
        }
    }
}
