package com.codecool.dao.database;

import com.codecool.dao.StorageDao;
import com.codecool.model.Storage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class DatabaseStorageDao extends AbstractDao implements StorageDao {

    public DatabaseStorageDao(Connection connection) {
        super(connection);
    }

    @Override
    public List<Storage> findAll(String id) throws SQLException {
        List<Storage> storages = new ArrayList<>();
        String sql = "SELECT id, name, user_id FROM storages WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, Integer.parseInt(id));
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    storages.add(fetchStorage(resultSet));
                }
            }
        }
        return storages;
    }


    @Override
    public Storage findById(String id) throws SQLException {
        String sql = "SELECT id, name, user_id FROM storages WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, Integer.parseInt(id));
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return fetchStorage(resultSet);
                }
            }
        }
        return null;
    }

    @Override
    public Storage add(String name, String userId) throws SQLException {
        if (name == null || "".equals(name)) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        String sql = "INSERT INTO storages (name, user_id) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            statement.setString(1, name);
            statement.setInt(2, Integer.parseInt(userId));
            executeInsert(statement);
            int id = fetchGeneratedId(statement);
            connection.commit();
            return new Storage(id, name, Integer.parseInt(userId));
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        } finally {
            connection.setAutoCommit(autoCommit);
        }
    }

    @Override
    public void delete(int userId, int storageId) throws SQLException {
        String sql = "DELETE FROM storages WHERE id = ? AND user_id = ?;";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, storageId);
            statement.setInt(2, userId);
            statement.executeUpdate();
        }
    }


    private Storage fetchStorage(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        int user_id = resultSet.getInt("user_id");
        return new Storage(id, name, user_id);
    }
}
