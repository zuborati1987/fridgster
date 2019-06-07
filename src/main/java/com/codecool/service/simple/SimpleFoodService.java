package com.codecool.service.simple;

import com.codecool.dao.FoodDao;
import com.codecool.model.Food;
import com.codecool.service.FoodService;
import com.codecool.service.exception.ServiceException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class SimpleFoodService extends AbstractService implements FoodService {

    private FoodDao foodDao;

    public SimpleFoodService(FoodDao foodDao) {
        this.foodDao = foodDao;
    }

    @Override
    public List<Food> findExpiries(String userId) throws SQLException {
        return foodDao.findAllByExpiry(userId);
    }

    @Override
    public List<Food> findAllByName(String userId) throws SQLException {
        return foodDao.findAllByName(userId);
    }

    @Override
    public List<Food> findAllByStorage(String userId) throws SQLException {
        return foodDao.findAllByStorage(userId);
    }

    @Override
    public List<Food> findAllByCategory(String userId) throws SQLException {
        return foodDao.findAllByCategory(userId);
    }

    @Override
    public List<Food> findAllByExpiry(String userId) throws SQLException {
        return foodDao.findAllByExpiry(userId);
    }

    public List<Food> findByName(String name, String userId) throws SQLException {
        return foodDao.findByName(name, userId);
    }

    public List<Food> findByStorage(String user_id, String storageId) throws SQLException, ServiceException {
        return foodDao.findByStorage(user_id, storageId);
    }

    public void delete(String foodIdChain, int userId) throws SQLException, ServiceException {
        String[] ids = foodIdChain.split(",");
        for (String id : ids) {
            foodDao.delete(fetchInt(id, "id"), userId);
        }
    }

    public void add(String name, int categoryId, double amount, int measurementId, int storageId, LocalDate expiry, int userId) throws SQLException{
        foodDao.add(name, categoryId, amount, measurementId, storageId, expiry, userId);
    }

    public void update(String name, int categoryId, double amount, int measurementId, int storageId, LocalDate expiry, int userId) throws SQLException {
        foodDao.update(name, categoryId, amount, measurementId, storageId, expiry, userId);
    }
}
