package com.codecool.dao.database;

import com.codecool.dao.MeasurementDao;
import com.codecool.model.Measurement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class DatabaseMeasurementDao extends AbstractDao implements MeasurementDao {

    public DatabaseMeasurementDao(Connection connection) {
        super(connection);
    }

    @Override
    public List<Measurement> findAll() throws SQLException {
        List<Measurement> measurements = new ArrayList<>();
        String sql = "SELECT id, name, user_id FROM measurements";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                measurements.add(fetchMeasurement(resultSet));
            }
        }
        return measurements;
    }

    @Override
    public Measurement findById(int id) throws SQLException {
        String sql = "SELECT id, name, user_id FROM measurements WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return fetchMeasurement(resultSet);
                }
            }
        }
        return null;
    }

    @Override
    public int findIdByName(String name, int userId) throws SQLException {
        String sql = "SELECT id FROM measurements WHERE name = ? AND user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setInt(2, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                }
            }
        }
        return 0;
    }

    @Override
    public int add(String name, int userId) throws SQLException {
        if (name == null || "".equals(name)) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        String sql = "INSERT INTO measurements (name, user_id) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            statement.setString(1, name);
            statement.setInt(2, userId);
            statement.executeQuery();
            int id = fetchGeneratedId(statement);
            connection.commit();
            return id;
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        } finally {
            connection.setAutoCommit(autoCommit);
        }
    }

    @Override
    public void delete(String name, int userId) throws SQLException {
        String sql = "DELETE FROM measurements WHERE name = ? AND user_id = ?;";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setInt(2, userId);
            statement.executeUpdate();
        }
    }


    private Measurement fetchMeasurement(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        int user_id = resultSet.getInt("user_id");
        return new Measurement(id, name, user_id);
    }
}
