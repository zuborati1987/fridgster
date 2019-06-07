package com.codecool.dao;

import com.codecool.model.ShoppingList;

import java.sql.SQLException;
import java.util.List;

public interface ShoppingDao {

    void add(int userId, int foodId) throws SQLException;

    void delete(int userId, int shoppingId) throws SQLException;

    List<ShoppingList> findAll(int userId) throws SQLException;

    void update(int shoppingId, int number, int userId) throws SQLException;

    List<ShoppingList> findAllActual(int userId) throws SQLException;
}
