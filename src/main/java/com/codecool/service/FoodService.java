package com.codecool.service;

import com.codecool.model.Food;
import com.codecool.service.exception.ServiceException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface FoodService {

    List<Food> findExpiries(String userId) throws SQLException, ServiceException;

    List<Food> findAllByName(String userId) throws SQLException, ServiceException;

    List<Food> findAllByStorage(String userId) throws SQLException, ServiceException;

    List<Food> findAllByCategory(String userId) throws SQLException, ServiceException;

    List<Food> findAllByExpiry(String userId) throws SQLException, ServiceException;

    List<Food> findByName(String name, String userId) throws SQLException;

    List<Food> findByStorage(String user_id, String storageId) throws SQLException, ServiceException;

    void delete(String foodIdChain, int userId) throws SQLException, ServiceException;

    void add(String name, int categoryId, double amount, int measurementId, int storageId, LocalDate expiry, int userId) throws SQLException;

    void update(String name, int categoryId, double amount, int measurementId, int storageId, LocalDate expiry, int userId) throws SQLException;
}
