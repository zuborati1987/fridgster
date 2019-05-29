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

@WebServlet("/protected/storage")
public class StorageServlet extends AbstractServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {

            User loggedInUser = (User) req.getSession().getAttribute("user");
            String id = String.valueOf(loggedInUser.getId());

            String storageId = req.getParameter("storageId");

            FoodDao foodDao = new DatabaseFoodDao(connection);
            FoodService foodService = new SimpleFoodService(foodDao);

            List<Food> food = foodService.findByStorage(id, storageId);


            sendMessage(resp, SC_OK, food);
        } catch (SQLException | ServiceException ex) {
            handleSqlError(resp, ex);
        }
    }
}
