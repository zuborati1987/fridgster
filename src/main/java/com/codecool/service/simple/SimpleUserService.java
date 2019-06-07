package com.codecool.service.simple;

import com.codecool.dao.UserDao;
import com.codecool.model.User;
import com.codecool.service.UserService;
import com.codecool.service.exception.ServiceException;

import java.sql.SQLException;
import java.util.List;

public class SimpleUserService extends AbstractService implements UserService {
    private final UserDao userDao;

    public SimpleUserService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User addUser(String password, String email) throws SQLException, ServiceException {
        try {
            return userDao.add(password, email);
        } catch (IllegalArgumentException ex) {
            throw new ServiceException(ex.getMessage());
        }
    }

    public List<User> findAll() throws SQLException, ServiceException {
        return userDao.findAll();
    }

    public void delete(String userIdChain) throws SQLException, ServiceException {
        String[] ids = userIdChain.split(",");
        for (String id : ids) {
            userDao.delete(fetchInt(id, "id"));
        }
    }
}
