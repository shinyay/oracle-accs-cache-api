package com.oracle.jp.shinyay.cache.servlet;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class SessionServlet extends HttpServlet {

    private HttpSession session ;
    private static final String VISITED = "visited";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println(createHTML(request));

        out.close();
    }

    protected String createHTML(HttpServletRequest request){
        StringBuffer sb = new StringBuffer();

        sb.append("<html>");
        sb.append("<head>");
        sb.append("<title>セッションテスト</title>");
        sb.append("<meta charset=\"UTF-8\">");
        sb.append("</head>");
        sb.append("<body>");

        sb.append("<p>");
        sb.append(displayCounter(request));
        sb.append("回のアクセスです");
        sb.append("</p>");

        sb.append("</body>");
        sb.append("</html>");

        return (new String(sb));
    }

    private String displayCounter(HttpServletRequest request) {

        session = request.getSession(false);
        if(session == null){
            session = request.getSession(true);
            session.setAttribute(VISITED, "1");
            return "初";
        } else {
            String visitedCount = (String) session.getAttribute(VISITED);
            session.setAttribute(VISITED, Integer.toString(Integer.parseInt(visitedCount) + 1));
            return visitedCount;
        }
    }
}
