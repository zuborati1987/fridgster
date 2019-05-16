package com.codecool.dao;

import java.sql.SQLException;

public interface ShoppingDao {

    void add(int userId, int foodId, double amount) throws SQLException;

    void delete(int foodId, int userId) throws SQLException;

}
