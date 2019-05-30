package com.codecool.dao;

import com.codecool.model.ShoppingList;

import java.sql.SQLException;
import java.util.List;

public interface ShoppingDao {

    void add(int userId, int foodId, double amount) throws SQLException;

    void delete(int userId, int shoppingId) throws SQLException;

    List<ShoppingList> findAll(int userId) throws SQLException;

}
