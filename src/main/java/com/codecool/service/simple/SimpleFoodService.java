package com.codecool.service.simple;

import com.codecool.dao.FoodDao;
import com.codecool.model.Food;
import com.codecool.service.FoodService;

import java.sql.SQLException;
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
}
