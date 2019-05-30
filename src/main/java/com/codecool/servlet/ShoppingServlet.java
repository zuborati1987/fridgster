package com.codecool.servlet;

import com.codecool.dao.FoodDao;
import com.codecool.dao.ShoppingDao;
import com.codecool.dao.database.DatabaseFoodDao;
import com.codecool.dao.database.DatabaseShoppingDao;
import com.codecool.model.Food;
import com.codecool.model.ShoppingList;
import com.codecool.model.User;
import com.codecool.service.FoodService;
import com.codecool.service.ShoppingService;
import com.codecool.service.exception.ServiceException;
import com.codecool.service.simple.SimpleFoodService;
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

@WebServlet("/protected/shopping")
public class ShoppingServlet extends AbstractServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {

            User loggedInUser = (User) req.getSession().getAttribute("user");
            int id = loggedInUser.getId();

            ShoppingDao shoppingDao = new DatabaseShoppingDao(connection);
            ShoppingService shoppingService = new SimpleShoppingService(shoppingDao);

            List<ShoppingList> shoppingList = shoppingService.findAll(id);


            sendMessage(resp, SC_OK, shoppingList);
        } catch (SQLException | ServiceException ex) {
            handleSqlError(resp, ex);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try (Connection connection = getConnection(req.getServletContext())) {
            ShoppingDao shoppingDao = new DatabaseShoppingDao(connection);
            ShoppingService shoppingService = new SimpleShoppingService(shoppingDao);

            User loggedInUser = (User) req.getSession().getAttribute("user");
            int userId = loggedInUser.getId();
            String shoppingIdChain = req.getParameter("shoppingIds");
            shoppingService.delete(shoppingIdChain, userId);

            sendMessage(resp, SC_OK, "Required item deleted");
        } catch (SQLException | ServiceException ex) {
            handleSqlError(resp, ex);
        }
    }
}
