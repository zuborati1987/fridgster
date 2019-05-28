package com.codecool.service;



import com.codecool.model.User;
import com.codecool.service.exception.ServiceException;

import java.sql.SQLException;

public interface UserService {

    User addUser(String password, String email) throws SQLException, ServiceException;

}
