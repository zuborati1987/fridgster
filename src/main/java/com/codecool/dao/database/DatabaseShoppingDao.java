package com.codecool.dao.database;

import com.codecool.dao.ShoppingDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseShoppingDao extends AbstractDao implements ShoppingDao {
    DatabaseShoppingDao(Connection connection) {
        super(connection);
    }

    @Override
    public void add(int userId, int foodId, double amount) throws SQLException {
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        String sql = "INSERT INTO shopping_lists (user_id, food_id, amount) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            statement.setInt(1, userId);
            statement.setInt(2, foodId);
            statement.setDouble(3, amount);
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
    public void delete(int foodId, int userId) throws SQLException {
        String sql = "DELETE FROM shopping_lists WHERE food_id = ? AND user_id = ?;";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, foodId);
            statement.setInt(2, userId);
            statement.executeUpdate();
        }
    }
}
