package com.codecool.servlet;

import com.codecool.dao.FoodDao;
import com.codecool.dao.database.DatabaseFoodDao;
import com.codecool.model.Food;
import com.codecool.model.User;
import com.codecool.service.FoodService;
import com.codecool.service.exception.ServiceException;
import com.codecool.service.simple.SimpleFoodService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static javax.servlet.http.HttpServletResponse.SC_OK;

@WebServlet("/protected/search")
public class SearchServlet extends AbstractServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {

            User loggedInUser = (User) req.getSession().getAttribute("user");
            String id = String.valueOf(loggedInUser.getId());
            String selected = req.getParameter("selected");

            FoodDao foodDao = new DatabaseFoodDao(connection);
            FoodService foodService = new SimpleFoodService(foodDao);

            List<Food> food = new ArrayList<>();
            if(selected.equals("name")) {
                food = foodService.findAllByName(id);
            } else if (selected.equals("storage")) {
                food = foodService.findAllByStorage(id);
            } else if (selected.equals("category")) {
                food = foodService.findAllByCategory(id);
            } else if (selected.equals("expiry")) {
                food = foodService.findAllByExpiry(id);
            } else if (selected.equals("search")) {
                String tofind = req.getParameter("tofind");
                food = foodService.findByName(tofind, id);
            }

            sendMessage(resp, SC_OK, food);
        } catch (SQLException | ServiceException ex) {
            handleSqlError(resp, ex);
        }
    }
}
