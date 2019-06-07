package com.codecool.dao;

import com.codecool.model.Storage;

import java.sql.SQLException;
import java.util.List;

public interface StorageDao {

    List<Storage> findAll(String id) throws SQLException;

    Storage findById(String id) throws SQLException;

    Storage add(String name, String userId) throws SQLException;

    void delete(int userId, int storageId) throws SQLException;
}
