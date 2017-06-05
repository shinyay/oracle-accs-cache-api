package com.oracle.jp.shinyay.cache.servlet;


import com.oracle.cloud.cache.basic.Cache;
import com.oracle.cloud.cache.basic.RemoteSessionProvider;
import com.oracle.cloud.cache.basic.Session;
import com.oracle.cloud.cache.basic.options.Transport;
import com.oracle.cloud.cache.basic.options.ValueType;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

public class CacheServlet extends HttpServlet {

    private HttpSession session ;
    private static final String VISITED = "visited";
    private static final String CCS_ENV_NAME = "CACHING_INTERNAL_CACHE_URL";
    private static final String CACHE_HOST = System.getenv(CCS_ENV_NAME);
    private static final String CACHE_PORT = "8080";
    private static final String CACHE_NAME = "sample";
    private static final String CACHE_URL = "http://" + CACHE_HOST + ":" + CACHE_PORT + "/ccs/";

    private static Session cacheSession;
    private static Cache cache;

    static {
        cacheSession = new RemoteSessionProvider(CACHE_URL).createSession(Transport.rest());
        cache = cacheSession.getCache(CACHE_NAME, ValueType.of(String.class));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Optional.ofNullable(request.getParameter("delete")).ifPresent(s -> deleteCache());

        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println(createHTML());
        out.close();
    }

    protected String createHTML(){
        StringBuffer sb = new StringBuffer();

        sb.append("<html>");
        sb.append("<head>");
        sb.append("<title>セッションテスト</title>");
        sb.append("<meta charset=\"UTF-8\">");
        sb.append("</head>");
        sb.append("<body>");

        sb.append("<p>");
        sb.append(displayCounter());
        sb.append("回のアクセスです");
        sb.append("</p>");

        sb.append("</body>");
        sb.append("</html>");

        return (new String(sb));
    }

    private String displayCounter() {

        String visitedCount = (String)cache.get(VISITED);

        if(visitedCount == null){
            cache.put(VISITED, "1");
            return "初";
        } else {
            cache.replace(VISITED, Integer.toString(Integer.parseInt(visitedCount) + 1));
            return visitedCount;
        }
    }

    private void deleteCache() {
        cache.remove(VISITED);
    }
}
