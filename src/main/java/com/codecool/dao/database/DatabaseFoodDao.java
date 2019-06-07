package com.codecool.dao.database;

import com.codecool.dao.FoodDao;
import com.codecool.model.Food;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DatabaseFoodDao extends AbstractDao implements FoodDao {

    public DatabaseFoodDao(Connection connection) {
        super(connection);
    }

    @Override
    public List<Food> findAll() throws SQLException {
        List<Food> foods = new ArrayList<>();
        String sql = "SELECT food.id, food.name, c.name AS category, amount, m.name AS measurement, s.name AS storage, expiry, food.user_id as user_id FROM food INNER JOIN categories c on food.category_id = c.id INNER JOIN measurements m on food.measurement_id = m.id INNER JOIN storages s on food.storage_id = s.id";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                foods.add(fetchFood(resultSet));
            }
        }
        return foods;
    }

    @Override
    public List<Food> findAllByName(String userId) throws SQLException {
        List<Food> foods = new ArrayList<>();
        String sql = "SELECT food.id, food.name, c.name AS category, amount, m.name AS measurement, s.name AS storage, expiry, food.user_id as user_id FROM food INNER JOIN categories c on food.category_id = c.id INNER JOIN measurements m on food.measurement_id = m.id INNER JOIN storages s on food.storage_id = s.id WHERE food.user_id = ? ORDER BY name ASC";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, Integer.parseInt(userId));
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    foods.add(fetchFood(resultSet));
                }
            }
        }
        return foods;
    }

    @Override
    public List<Food> findAllByCategory(String userId) throws SQLException {
        List<Food> foods = new ArrayList<>();
        String sql = "SELECT food.id, food.name, c.name AS category, amount, m.name AS measurement, s.name AS storage, expiry, food.user_id as user_id FROM food INNER JOIN categories c on food.category_id = c.id INNER JOIN measurements m on food.measurement_id = m.id INNER JOIN storages s on food.storage_id = s.id WHERE food.user_id = ? ORDER BY c.name ASC";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, Integer.parseInt(userId));
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    foods.add(fetchFood(resultSet));
                }
            }
        }
        return foods;
    }

    @Override
    public List<Food> findAllByStorage(String userId) throws SQLException {
        List<Food> foods = new ArrayList<>();
        String sql = "SELECT food.id, food.name, c.name AS category, amount, m.name AS measurement, s.name AS storage, expiry, food.user_id as user_id FROM food INNER JOIN categories c on food.category_id = c.id INNER JOIN measurements m on food.measurement_id = m.id INNER JOIN storages s on food.storage_id = s.id WHERE food.user_id = ? ORDER BY s.name ASC";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, Integer.parseInt(userId));
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    foods.add(fetchFood(resultSet));
                }
            }
        }
        return foods;
    }

    @Override
    public List<Food> findAllByExpiry(String userId) throws SQLException {
        List<Food> foods = new ArrayList<>();
        String sql = "SELECT food.id, food.name, c.name AS category, amount, m.name AS measurement, s.name AS storage, expiry, food.user_id as user_id FROM food INNER JOIN categories c on food.category_id = c.id INNER JOIN measurements m on food.measurement_id = m.id INNER JOIN storages s on food.storage_id = s.id WHERE food.user_id = ? ORDER BY expiry ASC";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, Integer.parseInt(userId));
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    foods.add(fetchFood(resultSet));
                }
            }
        }
        return foods;
    }

    @Override
    public Food findById(int id) throws SQLException {
        String sql = "SELECT food.id, food.name, c.name AS category, amount, m.name AS measurement, s.name AS storage, expiry, food.user_id as user_id FROM food INNER JOIN categories c on food.category_id = c.id INNER JOIN measurements m on food.measurement_id = m.id INNER JOIN storages s on food.storage_id = s.id WHERE id = ?";
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
    public List<Food> findByName(String name, String userId) throws SQLException {
        List<Food> foods = new ArrayList<>();
        String sql = "SELECT food.id, food.name, c.name AS category, amount, m.name AS measurement, s.name AS storage, expiry, food.user_id as user_id FROM food INNER JOIN categories c on food.category_id = c.id INNER JOIN measurements m on food.measurement_id = m.id INNER JOIN storages s on food.storage_id = s.id WHERE food.name LIKE ? AND food.user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "%" + name + "%");
            statement.setInt(2, Integer.parseInt(userId));
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    foods.add(fetchFood(resultSet));
                }
            }
        }
        return foods;
    }

    @Override
    public List<Food> findByStorage(String user_id, String storageId) throws SQLException {
        List<Food> foods = new ArrayList<>();
        String sql = "SELECT food.id, food.name, c.name AS category, amount, m.name AS measurement, s.name AS storage, expiry, food.user_id as user_id FROM food INNER JOIN categories c on food.category_id = c.id INNER JOIN measurements m on food.measurement_id = m.id INNER JOIN storages s on food.storage_id = s.id WHERE storage_id = ? AND food.user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, Integer.parseInt(storageId));
            statement.setInt(2, Integer.parseInt(user_id));
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    foods.add(fetchFood(resultSet));
                }
            }
        }
        return foods;
    }

    @Override
    public void add(String name, int categoryId, double amount, int measurementId, int storageId, LocalDate expiry, int userId) throws SQLException {
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
            statement.setObject(6, expiry);
            statement.setInt(7, userId);
            executeInsert(statement);
            connection.commit();
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        } finally {
            connection.setAutoCommit(autoCommit);
        }
    }

    @Override
    public void update(String name, int categoryId, double amount, int measurementId, int storageId, LocalDate expiry, int userId) throws SQLException {
        String sql = "UPDATE food SET amount = (amount + ?)  WHERE name = ? AND category_id = ? AND measurement_id = ? AND storage_id = ? AND expiry = ? AND user_id = ?;";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDouble(1, amount);
            statement.setString(2, name);
            statement.setInt(3, categoryId);
            statement.setInt(4, measurementId);
            statement.setInt(5, storageId);
            statement.setObject(6, expiry);
            statement.setInt(7, userId);
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(int foodId, int userId) throws SQLException {
        String sql = "DELETE FROM food WHERE id = ? AND user_id = ?;";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, foodId);
            statement.setInt(2, userId);
            statement.executeUpdate();
        }
    }

    private Food fetchFood(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String category = resultSet.getString("category");
        double amount = resultSet.getDouble("amount");
        String measurement = resultSet.getString("measurement");
        String storage = resultSet.getString("storage");
        Date expiry = resultSet.getDate("expiry");
        LocalDate localDate = expiry.toLocalDate();
        int userId = resultSet.getInt("user_id");
        return new Food(id, name, category, amount, measurement, storage, localDate, userId);
    }


}
