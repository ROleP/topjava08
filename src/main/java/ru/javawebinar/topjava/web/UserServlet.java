package ru.javawebinar.topjava.web;

import org.slf4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.slf4j.LoggerFactory.*;

/**
 * Created by rolep on 25/09/16.
 */
public class UserServlet extends HttpServlet {

    private static final Logger LOG = getLogger(UserServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("redirect to userList");
        //req.getRequestDispatcher("/userList.jsp").forward(req, resp);
        resp.sendRedirect("userList.jsp");
    }
}
