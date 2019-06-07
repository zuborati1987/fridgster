package com.codecool.service;


import com.codecool.model.User;
import com.codecool.service.exception.ServiceException;

import java.sql.SQLException;
import java.util.List;

public interface UserService {

    User addUser(String password, String email) throws SQLException, ServiceException;

    List<User> findAll() throws SQLException, ServiceException;

    void delete(String userIdChain) throws SQLException, ServiceException;

}
