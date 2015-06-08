package com.shibsted.mvc.http;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

public class CookiesFilter extends Filter {

    @Override
    public String description() {
        return "Parses request to get Cookies";
    }

    @Override
    public void doFilter(HttpExchange httpExchange, Chain chain) throws IOException {
        HashMap cookiesHash = getCookies(httpExchange);
        httpExchange.setAttribute("cookies", cookiesHash);

        chain.doFilter(httpExchange);
    }

    public HashMap getCookies(HttpExchange httpExchange) {
        HashMap cookiesHash = new HashMap();
        Headers reqHeaders = httpExchange.getRequestHeaders();

        if (!reqHeaders.containsKey("Cookie")) {
            return cookiesHash;
        }

        List<String> cookies = reqHeaders.get("Cookie");
        StringTokenizer tokenizer = new StringTokenizer(cookies.get(0), ";");

        for (String cookie : cookies) {
            if (cookie.contains("user")) {
                while (tokenizer.hasMoreTokens()) {
                    String token = tokenizer.nextToken();
                    StringTokenizer cookieTokenizer = new StringTokenizer(token, "=");
                    String key = cookieTokenizer.nextToken().trim();
                    String value = "";
                    if (cookieTokenizer.hasMoreTokens()) {
                        value = cookieTokenizer.nextToken().trim();
                    }

                    cookiesHash.put(key, value);
                }
            }
        }
        return cookiesHash;
    }
}
