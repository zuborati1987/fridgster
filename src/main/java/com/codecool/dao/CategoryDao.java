package com.codecool.dao;

import com.codecool.model.Category;

import java.sql.SQLException;
import java.util.List;

public interface CategoryDao {

    List<Category> findAll() throws SQLException;

    Category findById(int id) throws SQLException;

    int findIdByName(String name, int userId) throws SQLException;

    int add(String name, int userId) throws SQLException;

    void delete(String name, int userId) throws SQLException;
}
