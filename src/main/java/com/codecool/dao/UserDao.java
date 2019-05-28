package com.codecool.dao;

import com.codecool.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {

    List<User> findAll() throws SQLException;

    User findByEmail(String email) throws SQLException;

    User add(String email, String password) throws SQLException;


}
