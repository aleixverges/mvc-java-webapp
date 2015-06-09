package com.shibsted.mvc.tests;

import com.shibsted.mvc.controller.PageController;
import com.shibsted.mvc.controller.PageControllerFactory;
import com.shibsted.mvc.http.Handler;
import com.sun.net.httpserver.HttpExchange;
import junit.framework.TestCase;
import org.junit.Test;
import org.mockito.Mockito;

import java.net.URI;
import java.util.Map;

public class HandlerTest extends TestCase {

    private Handler handler;
    private PageControllerFactory pageControllerFactory;

    protected void setUp() {
        this.pageControllerFactory = Mockito.mock(PageControllerFactory.class);
        this.handler = new Handler(this.pageControllerFactory);
    }

    @Test
    public void testShouldHandleLoginContext() {
        HttpExchange httpExchangeMock = Mockito.mock(HttpExchange.class);
        URI uri = URI.create("/");
        PageController pageControllerMock = Mockito.mock(PageController.class);
        Map parametersMock = Mockito.mock(Map.class);

        Mockito.when(this.pageControllerFactory.build(httpExchangeMock)).thenReturn(pageControllerMock);
        Mockito.when(httpExchangeMock.getRequestURI()).thenReturn(uri);
        Mockito.when(httpExchangeMock.getAttribute("parameters")).thenReturn(parametersMock);

        this.handler.handle(httpExchangeMock);

        Mockito.verify(pageControllerMock).loginAction(parametersMock);
    }

    @Test
    public void testShouldHandlePageContext() {
        HttpExchange httpExchangeMock = Mockito.mock(HttpExchange.class);
        URI uri = URI.create("/page1");
        PageController pageControllerMock = Mockito.mock(PageController.class);
        Map cookiesMock = Mockito.mock(Map.class);

        Mockito.when(this.pageControllerFactory.build(httpExchangeMock)).thenReturn(pageControllerMock);
        Mockito.when(httpExchangeMock.getRequestURI()).thenReturn(uri);
        Mockito.when(httpExchangeMock.getAttribute("cookies")).thenReturn(cookiesMock);

        this.handler.handle(httpExchangeMock);

        Mockito.verify(pageControllerMock).pageAction(cookiesMock);
    }

    @Test
    public void testShouldHandleLogoutContext() {
        HttpExchange httpExchangeMock = Mockito.mock(HttpExchange.class);
        URI uri = URI.create("/logout");
        PageController pageControllerMock = Mockito.mock(PageController.class);

        Mockito.when(this.pageControllerFactory.build(httpExchangeMock)).thenReturn(pageControllerMock);
        Mockito.when(httpExchangeMock.getRequestURI()).thenReturn(uri);

        this.handler.handle(httpExchangeMock);

        Mockito.verify(pageControllerMock).logoutAction();
    }
}