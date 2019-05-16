package com.codecool.dao;

import com.codecool.model.Food;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface FoodDao {

    List<Food> findAll() throws SQLException;

    List<Food> findAllByName() throws SQLException;

    List<Food> findAllByCategory() throws SQLException;

    List<Food> findAllByStorage() throws SQLException;

    List<Food> findAllByExpiry() throws SQLException;

    Food findById(int id) throws SQLException;

    Food findByName(String name, int userId) throws SQLException;

    Food add(String name, int categoryId, double amount, int measurementId, int storageId, Date expiry, int userId) throws SQLException;

    void delete(String name, int userId) throws SQLException;
}
