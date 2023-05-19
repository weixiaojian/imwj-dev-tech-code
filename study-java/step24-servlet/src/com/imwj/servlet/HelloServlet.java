package com.imwj.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * @author wj
 * @create 2023-05-16 17:39
 */
public class HelloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            resp.getWriter().println("<h1>Hello Servlet!</h1>");
            resp.getWriter().println(new Date().toLocaleString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
