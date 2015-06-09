package com.shibsted.mvc.tests;

import com.shibsted.mvc.http.CookiesFilter;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import junit.framework.TestCase;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class CookiesFilterTest extends TestCase {

    private CookiesFilter cookiesFilter;

    public CookiesFilterTest(String name) {
        super(name);
    }

    protected void setUp() {
        this.cookiesFilter = new CookiesFilter();
    }

    @Test
    public void testShouldReturnEmptyCookiesHash() {
        HttpExchange httpExchangeMock = Mockito.mock(HttpExchange.class);
        Headers requestHeadersMock = createRequestHeadersMock(false);
        Mockito.when(httpExchangeMock.getRequestHeaders()).thenReturn(requestHeadersMock);

        HashMap actual = this.cookiesFilter.getCookies(httpExchangeMock);

        assertTrue(actual.isEmpty());
    }

    @Test
    public void testShouldGetCookiesHashWhenCookiesAvailable() {
        HttpExchange httpExchangeMock = Mockito.mock(HttpExchange.class);
        Headers requestHeadersMock = createRequestHeadersMock(true);

        List<String> cookiesMock = new LinkedList<>();
        cookiesMock.add(0, "user=; role=");

        Mockito.when(requestHeadersMock.get("Cookie")).thenReturn(cookiesMock);
        Mockito.when(httpExchangeMock.getRequestHeaders()).thenReturn(requestHeadersMock);

        HashMap actual = this.cookiesFilter.getCookies(httpExchangeMock);
        assertTrue(actual.containsKey("user"));
        assertTrue(actual.containsKey("role"));
    }

    @Test
    public void testShouldGetCookiesHashWithValues() {
        HttpExchange httpExchangeMock = Mockito.mock(HttpExchange.class);
        Headers requestHeadersMock = createRequestHeadersMock(true);

        List<String> cookiesMock = new LinkedList<>();
        cookiesMock.add(0, "user=some_user; role=some_role");

        Mockito.when(requestHeadersMock.get("Cookie")).thenReturn(cookiesMock);
        Mockito.when(httpExchangeMock.getRequestHeaders()).thenReturn(requestHeadersMock);

        HashMap actual = this.cookiesFilter.getCookies(httpExchangeMock);
        assertEquals("some_user", actual.get("user"));
        assertEquals("some_role", actual.get("role"));
    }

    private Headers createRequestHeadersMock(boolean value) {
        Headers requestHeadersMock = Mockito.mock(Headers.class);
        Mockito.when(requestHeadersMock.containsKey("Cookie")).thenReturn(value);
        return requestHeadersMock;
    }
}