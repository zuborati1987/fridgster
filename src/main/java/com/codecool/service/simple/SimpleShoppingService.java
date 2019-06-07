package com.codecool.service.simple;

import com.codecool.dao.ShoppingDao;
import com.codecool.model.ShoppingList;
import com.codecool.service.ShoppingService;
import com.codecool.service.exception.ServiceException;

import java.sql.SQLException;
import java.util.List;

public class SimpleShoppingService extends AbstractService implements ShoppingService {

    private ShoppingDao shoppingDao;

    public SimpleShoppingService(ShoppingDao shoppingDao) {
        this.shoppingDao = shoppingDao;
    }

    public List<ShoppingList> findAll(int userId) throws SQLException, ServiceException {
        return shoppingDao.findAll(userId);
    }

    public List<ShoppingList> findAllActual(int userId) throws SQLException, ServiceException {
        return shoppingDao.findAllActual(userId);
    }

    public void delete(String shoppingIdChain, int userId) throws SQLException, ServiceException {
        String[] ids = shoppingIdChain.split(",");
        for (String id : ids) {
            shoppingDao.delete(userId, fetchInt(id, "id"));
        }
    }

    public void add(String foodIdChain, int userId) throws SQLException, ServiceException {
        String[] ids = foodIdChain.split(",");
        for (String id : ids) {
            shoppingDao.add(userId, fetchInt(id, "id"));
        }
    }

    public void update(int shoppingId, int number, int userId) throws SQLException, ServiceException {
        shoppingDao.update(shoppingId, number, userId);
    }
}
