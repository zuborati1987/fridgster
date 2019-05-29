package com.codecool.dao;

import com.codecool.model.Storage;

import java.sql.SQLException;
import java.util.List;

public interface StorageDao {

    List<Storage> findAll(String id) throws SQLException;

    Storage findById(String id) throws SQLException;

    Storage add(String name, int userId) throws SQLException;

    void update(int userId, int scheduleId, String newName) throws SQLException;

    void delete(String name, int userId) throws SQLException;
}
