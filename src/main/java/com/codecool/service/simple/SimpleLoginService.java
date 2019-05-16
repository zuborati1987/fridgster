package com.codecool.service.simple;

import com.codecool.dao.UserDao;
import com.codecool.model.User;
import com.codecool.service.LoginService;
import com.codecool.service.exception.ServiceException;
import javafx.concurrent.Service;

import javax.sql.rowset.serial.SerialException;
import java.sql.SQLException;

public final class SimpleLoginService implements LoginService {

    private final UserDao userDao;

    public SimpleLoginService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User loginUser(String email, String password) throws SQLException, ServiceException {
        try {
            User user = userDao.findByEmail(email);
            if (user == null || !user.getPassword().equals(password)) {
                throw new ServiceException("Bad login");
            }
            return user;
        } catch (IllegalArgumentException ex) {
            throw new ServiceException(ex.getMessage());
        }
    }
}
