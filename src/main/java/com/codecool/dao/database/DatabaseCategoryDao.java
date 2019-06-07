package com.codecool.dao.database;

import com.codecool.dao.CategoryDao;
import com.codecool.model.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class DatabaseCategoryDao extends AbstractDao implements CategoryDao {

    public DatabaseCategoryDao(Connection connection) {
        super(connection);
    }

    @Override
    public List<Category> findAll() throws SQLException {
        List<Category> categorys = new ArrayList<>();
        String sql = "SELECT id, name, user_id FROM categories";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                categorys.add(fetchCategory(resultSet));
            }
        }
        return categorys;
    }

    @Override
    public Category findById(int id) throws SQLException {
        String sql = "SELECT id, name, user_id FROM categories WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return fetchCategory(resultSet);
                }
            }
        }
        return null;
    }

    @Override
    public int findIdByName(String name, int userId) throws SQLException {
        String sql = "SELECT id FROM categories WHERE name = ? AND user_id = ?";
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
        String sql = "INSERT INTO categories (name, user_id) VALUES (?, ?)";
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
        String sql = "DELETE FROM categories WHERE name = ? AND user_id = ?;";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setInt(2, userId);
            statement.executeUpdate();
        }
    }


    private Category fetchCategory(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        int user_id = resultSet.getInt("user_id");
        return new Category(id, name, user_id);
    }
}
