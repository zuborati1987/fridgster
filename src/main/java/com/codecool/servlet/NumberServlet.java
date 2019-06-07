package com.codecool.servlet;

import com.codecool.dao.ShoppingDao;
import com.codecool.dao.database.DatabaseShoppingDao;
import com.codecool.model.ShoppingList;
import com.codecool.model.User;
import com.codecool.service.ShoppingService;
import com.codecool.service.exception.ServiceException;
import com.codecool.service.simple.SimpleShoppingService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static javax.servlet.http.HttpServletResponse.SC_OK;

@WebServlet("/protected/number")
public class NumberServlet extends AbstractServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try (Connection connection = getConnection(req.getServletContext())) {
            ShoppingDao shoppingDao = new DatabaseShoppingDao(connection);
            ShoppingService shoppingService = new SimpleShoppingService(shoppingDao);

            User loggedInUser = (User) req.getSession().getAttribute("user");
            int userId = loggedInUser.getId();

            int shoppingId = Integer.parseInt(req.getParameter("id"));
            System.out.println(shoppingId);
            int number = Integer.parseInt(req.getParameter("number"));
            System.out.println(number);
            shoppingService.update(shoppingId, number, userId);

            sendMessage(resp, SC_OK, "Required item updated");
        } catch (SQLException | ServiceException ex) {
            handleSqlError(resp, ex);
        }
    }

}
