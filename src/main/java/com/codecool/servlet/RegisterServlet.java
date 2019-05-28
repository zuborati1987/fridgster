package com.codecool.servlet;


import com.codecool.dao.UserDao;
import com.codecool.dao.database.DatabaseUserDao;
import com.codecool.service.UserService;
import com.codecool.service.exception.ServiceException;
import com.codecool.service.simple.SimpleUserService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/register")
public class RegisterServlet extends AbstractServlet{

    private static final String SQL_ERROR_CODE_UNIQUE_VIOLATION = "23505";


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            UserDao userDao = new DatabaseUserDao(connection);
            UserService userService = new SimpleUserService(userDao);

            String email = req.getParameter("email");
            String password = req.getParameter("password");
            String repassword = req.getParameter("repassword");

            if(password.equals(repassword)) {
                userService.addUser(password, email);
            } else {
                sendMessage(resp, HttpServletResponse.SC_BAD_REQUEST, "Passwords do not match");

            }

        } catch (SQLException ex) {
            if (SQL_ERROR_CODE_UNIQUE_VIOLATION.equals(ex.getSQLState())) {
                sendMessage(resp, HttpServletResponse.SC_BAD_REQUEST, "User has been already registered");
            } else {
                handleSqlError(resp, ex);
            }
        } catch (ServiceException ex) {
            sendMessage(resp, HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
        }
    }
}
