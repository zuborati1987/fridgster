package com.codecool.service;

import com.codecool.model.User;
import com.codecool.service.exception.ServiceException;

import java.sql.SQLException;

public interface LoginService {

    User loginUser(String email, String password) throws SQLException, ServiceException;
}
