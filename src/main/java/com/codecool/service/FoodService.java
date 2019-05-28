package com.codecool.service;

import com.codecool.model.Food;
import com.codecool.service.exception.ServiceException;

import java.sql.SQLException;
import java.util.List;

public interface FoodService{

    List<Food> findExpiries(String userId) throws SQLException, ServiceException;
}
