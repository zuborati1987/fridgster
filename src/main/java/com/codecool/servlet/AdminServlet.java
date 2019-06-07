package com.codecool.servlet;

import com.codecool.dao.UserDao;
import com.codecool.dao.database.DatabaseUserDao;
import com.codecool.model.User;
import com.codecool.service.UserService;
import com.codecool.service.exception.ServiceException;
import com.codecool.service.simple.SimpleUserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static javax.servlet.http.HttpServletResponse.SC_OK;

@WebServlet("/protected/admin")
public class AdminServlet extends AbstractServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {

            User loggedInUser = (User) req.getSession().getAttribute("user");
            String id = String.valueOf(loggedInUser.getId());

            UserDao userDao = new DatabaseUserDao(connection);
            UserService userService = new SimpleUserService(userDao);

            List<User> users = userService.findAll();
            sendMessage(resp, SC_OK, users);
        } catch (SQLException | ServiceException ex) {
            handleSqlError(resp, ex);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try (Connection connection = getConnection(req.getServletContext())) {
            UserDao userDao = new DatabaseUserDao(connection);
            UserService userService = new SimpleUserService(userDao);

            User loggedInUser = (User) req.getSession().getAttribute("user");
            int userId = loggedInUser.getId();
            String userIdChain = req.getParameter("userIds");
            userService.delete(userIdChain);

            sendMessage(resp, SC_OK, "User deleted");
        } catch (SQLException | ServiceException ex) {
            handleSqlError(resp, ex);
        }
    }
}
