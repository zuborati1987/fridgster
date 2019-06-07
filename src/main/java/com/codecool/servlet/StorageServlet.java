package com.codecool.servlet;

import com.codecool.dao.CategoryDao;
import com.codecool.dao.FoodDao;
import com.codecool.dao.MeasurementDao;
import com.codecool.dao.database.DatabaseCategoryDao;
import com.codecool.dao.database.DatabaseFoodDao;
import com.codecool.dao.database.DatabaseMeasurementDao;
import com.codecool.model.Food;
import com.codecool.model.User;
import com.codecool.service.CategoryService;
import com.codecool.service.FoodService;
import com.codecool.service.MeasurementService;
import com.codecool.service.exception.ServiceException;
import com.codecool.service.simple.SimpleCategoryService;
import com.codecool.service.simple.SimpleFoodService;
import com.codecool.service.simple.SimpleMeasurementService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            
            User loggedInUser = (User) req.getSession().getAttribute("user");
            int id = loggedInUser.getId();
            
            int categoryId;
            int measurementId;
            
            int storageId = Integer.parseInt(req.getParameter("storageId"));
            String foodName = req.getParameter("newFoodName");
            String categoryName = req.getParameter("newFoodCat");
            String measurementName = req.getParameter("newFoodMeasurement");
            Double amount = Double.parseDouble(req.getParameter("newFoodAmount"));
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String dateToParse = req.getParameter("newFoodExpiry");
            LocalDate expiry = LocalDate.parse(dateToParse, dateFormat);

            FoodDao foodDao = new DatabaseFoodDao(connection);
            FoodService foodService = new SimpleFoodService(foodDao);

            MeasurementDao measurementDao = new DatabaseMeasurementDao(connection);
            MeasurementService measurementService = new SimpleMeasurementService(measurementDao);

            CategoryDao categoryDao = new DatabaseCategoryDao(connection);
            CategoryService categoryService = new SimpleCategoryService(categoryDao);

            try {
                categoryId = categoryService.add(categoryName, id);
            } catch (SQLException ex) {
                categoryId = categoryService.findIdByName(categoryName, id);
            }

            try {
                measurementId = measurementService.add(measurementName, id);
            } catch (SQLException ex) {
                measurementId = measurementService.findIdByName(measurementName, id);
            }

            try {
                foodService.add(foodName, categoryId, amount, measurementId, storageId, expiry, id);
            } catch (SQLException ex) {
                foodService.update(foodName, categoryId, amount, measurementId, storageId, expiry, id);
            }

            sendMessage(resp, SC_OK, "Food item added to storage");
        } catch (SQLException | ServiceException ex) {
            handleSqlError(resp, ex);
        }
    }
}
