package com.codecool.servlet;

import com.codecool.dao.FoodDao;
import com.codecool.dao.StorageDao;
import com.codecool.dao.database.DatabaseFoodDao;
import com.codecool.dao.database.DatabaseStorageDao;
import com.codecool.model.Food;
import com.codecool.model.Storage;
import com.codecool.model.User;
import com.codecool.service.FoodService;
import com.codecool.service.StorageService;
import com.codecool.service.exception.ServiceException;
import com.codecool.service.simple.SimpleFoodService;
import com.codecool.service.simple.SimpleStorageService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static javax.servlet.http.HttpServletResponse.SC_OK;

@WebServlet("/protected/storages")
public class StoragesServlet extends AbstractServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {

            User loggedInUser = (User) req.getSession().getAttribute("user");
            String id = String.valueOf(loggedInUser.getId());

            StorageDao storageDao = new DatabaseStorageDao(connection);
            StorageService storageService = new SimpleStorageService(storageDao);

            List<Storage> storages = storageService.findAll(id);
            sendMessage(resp, SC_OK, storages);
        } catch (SQLException | ServiceException ex) {
            handleSqlError(resp, ex);
        }
    }
}
