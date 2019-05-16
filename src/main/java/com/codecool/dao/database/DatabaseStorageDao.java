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
    public List<Storage> findAll() throws SQLException {
        List<Storage> storages = new ArrayList<>();
        String sql = "SELECT id, name, user_id FROM storages";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                storages.add(fetchStorage(resultSet));
            }
        }
        return storages;
    }


    @Override
    public Storage findById(int id) throws SQLException {
        String sql = "SELECT id, name, user_id FROM storages WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return fetchStorage(resultSet);
                }
            }
        }
        return null;
    }

    @Override
    public Storage add(String name, int userId) throws SQLException {
        if (name == null || "".equals(name)) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        String sql = "INSERT INTO storages (name, user_id) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            statement.setString(1, name);
            statement.setInt(2, userId);
            executeInsert(statement);
            int id = fetchGeneratedId(statement);
            connection.commit();
            return new Storage(id, name, userId);
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        } finally {
            connection.setAutoCommit(autoCommit);
        }
    }

    @Override
    public void update(int userId, int scheduleId, String newName) throws SQLException {
        String sql = "UPDATE Schedule SET name = ?  WHERE users_id = ? AND id = ?;";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, newName);
            statement.setInt(2, scheduleId);
            statement.setInt(3, scheduleId);
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(String name, int userId) throws SQLException {
        String sql = "DELETE FROM storages WHERE name = ? AND user_id = ?;";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
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
