package com.codecool.dao.database;

import com.codecool.dao.FoodDao;
import com.codecool.model.Food;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseFoodDao extends AbstractDao implements FoodDao {

    DatabaseFoodDao(Connection connection) {
        super(connection);
    }

    @Override
    public List<Food> findAll() throws SQLException {
        List<Food> foods = new ArrayList<>();
        String sql = "SELECT * FROM food";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                foods.add(fetchFood(resultSet));
            }
        }
        return foods;
    }

    @Override
    public List<Food> findAllByName() throws SQLException {
        List<Food> foods = new ArrayList<>();
        String sql = "SELECT * FROM food ORDER BY name ASC";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                foods.add(fetchFood(resultSet));
            }
        }
        return foods;
    }

    @Override
    public List<Food> findAllByCategory() throws SQLException {
        List<Food> foods = new ArrayList<>();
        String sql = "SELECT * FROM food INNER JOIN categories c on food.category_id = c.id ORDER BY c.name ASC";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                foods.add(fetchFood(resultSet));
            }
        }
        return foods;
    }

    @Override
    public List<Food> findAllByStorage() throws SQLException {
        List<Food> foods = new ArrayList<>();
        String sql = "SELECT * FROM food INNER JOIN storages s on food.storage_id = s.id ORDER BY s.name ASC";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                foods.add(fetchFood(resultSet));
            }
        }
        return foods;
    }

    @Override
    public List<Food> findAllByExpiry() throws SQLException {
        List<Food> foods = new ArrayList<>();
        String sql = "SELECT * FROM food ORDER BY expiry ASC";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                foods.add(fetchFood(resultSet));
            }
        }
        return foods;
    }

    @Override
    public Food findById(int id) throws SQLException {
        String sql = "SELECT * FROM food WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return fetchFood(resultSet);
                }
            }
        }
        return null;
    }

    @Override
    public Food findByName(String name, int userId) throws SQLException {
        String sql = "SELECT * FROM food WHERE name = ? AND user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setInt(2, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return fetchFood(resultSet);
                }
            }
        }
        return null;
    }

    @Override
    public Food add(String name, int categoryId, double amount, int measurementId, int storageId, Date expiry, int userId) throws SQLException {
        if (name == null || "".equals(name)) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (amount == 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        String sql = "INSERT INTO food (name, category_id, amount, measurement_id, storage_id, expiry, user_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            statement.setString(1, name);
            statement.setInt(2, categoryId);
            statement.setDouble(3, amount);
            statement.setInt(4, measurementId);
            statement.setInt(5, storageId);
            statement.setDate(6, expiry);
            statement.setInt(7, userId);
            executeInsert(statement);
            int id = fetchGeneratedId(statement);
            connection.commit();
            return new Food(id, name, categoryId, amount, measurementId, storageId, expiry, userId);
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        } finally {
            connection.setAutoCommit(autoCommit);
        }
    }

    @Override
    public void delete(String name, int userId) throws SQLException {
        String sql = "DELETE FROM food WHERE name = ? AND user_id = ?;";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setInt(2, userId);
            statement.executeUpdate();
        }
    }

    private Food fetchFood(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        int categoryId = resultSet.getInt("category_id");
        double amount = resultSet.getDouble("amount");
        int measurementId = resultSet.getInt("measurement_id");
        int storageId = resultSet.getInt("storage_id");
        Date expiry = resultSet.getDate("expiry");
        int userId = resultSet.getInt("user_id");
        return new Food(id, name, categoryId, amount, measurementId, storageId, expiry, userId);
    }


}
