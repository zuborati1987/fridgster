package com.codecool.dao;

import com.codecool.model.Food;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface FoodDao {

    List<Food> findAll() throws SQLException;

    List<Food> findAllByName(String userId) throws SQLException;

    List<Food> findAllByCategory(String userId) throws SQLException;

    List<Food> findAllByStorage(String userId) throws SQLException;

    List<Food> findAllByExpiry(String userId) throws SQLException;

    Food findById(int id) throws SQLException;

    Food findByName(String name, int userId) throws SQLException;

    Food add(String name, int categoryId, double amount, int measurementId, int storageId, LocalDate expiry, int userId) throws SQLException;

    void delete(String name, int userId) throws SQLException;
}
