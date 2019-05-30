package com.codecool.service.simple;

import com.codecool.dao.FoodDao;
import com.codecool.dao.ShoppingDao;
import com.codecool.model.Food;
import com.codecool.model.ShoppingList;
import com.codecool.service.FoodService;
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

    public void delete(String shoppingIdChain, int userId) throws SQLException, ServiceException {
        String[] ids = shoppingIdChain.split(",");
        for (String id : ids) {
            shoppingDao.delete(userId, fetchInt(id, "id"));
        }
    }
}
