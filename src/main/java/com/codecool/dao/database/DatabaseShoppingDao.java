package com.codecool.dao.database;

import com.codecool.dao.ShoppingDao;
import com.codecool.model.ShoppingList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseShoppingDao extends AbstractDao implements ShoppingDao {
    public DatabaseShoppingDao(Connection connection) {
        super(connection);
    }


    @Override
    public List<ShoppingList> findAll(int userId) throws SQLException {
        List<ShoppingList> shoppingList = new ArrayList<>();
        String sql = "SELECT f.id AS id, f.name AS name, m.name AS measurement, shopping_lists.amount AS amount FROM shopping_lists INNER JOIN food f on shopping_lists.food_id = f.id INNER JOIN measurements m on f.measurement_id = m.id WHERE shopping_lists.user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    shoppingList.add(fetchShoppingList(resultSet));
                }
            }
        }
        return shoppingList;
    }

    @Override
    public List<ShoppingList> findAllActual(int userId) throws SQLException {
        List<ShoppingList> shoppingList = new ArrayList<>();
        String sql = "SELECT f.id AS id, f.name AS name, m.name AS measurement, (shopping_lists.amount - f.amount) AS amount FROM shopping_lists INNER JOIN food f on shopping_lists.food_id = f.id INNER JOIN measurements m on f.measurement_id = m.id WHERE shopping_lists.user_id = ? AND shopping_lists.amount > f.amount";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    shoppingList.add(fetchShoppingList(resultSet));
                }
            }
        }
        return shoppingList;
    }

    @Override
    public void add(int userId, int foodId) throws SQLException {
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        String sql = "SELECT \"shopping_adder\"(?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            statement.setInt(1, userId);
            statement.setInt(2, foodId);
            statement.execute();
            connection.commit();
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        } finally {
            connection.setAutoCommit(autoCommit);
        }
    }

    @Override
    public void delete(int userId, int shoppingId) throws SQLException {
        String sql = "DELETE FROM shopping_lists WHERE food_id = ? AND user_id = ?;";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, shoppingId);
            statement.setInt(2, userId);
            statement.executeUpdate();
        }
    }

    @Override
    public void update(int shoppingId, int number, int userId) throws SQLException {
        String sql = "UPDATE shopping_lists SET amount = (amount + (? - amount))  WHERE user_id = ? AND food_id = ?;";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDouble(1, number);
            statement.setInt(2, userId);
            statement.setInt(3, shoppingId);
            statement.executeUpdate();
        }
    }

    private ShoppingList fetchShoppingList(ResultSet resultSet) throws SQLException {
        int foodId = resultSet.getInt("id");
        String name = resultSet.getString("name");
        double amount = resultSet.getDouble("amount");
        String measurement = resultSet.getString("measurement");
        return new ShoppingList(foodId, name, amount, measurement);
    }
}
