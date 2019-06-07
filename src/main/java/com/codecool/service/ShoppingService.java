package com.codecool.service;

import com.codecool.model.ShoppingList;
import com.codecool.service.exception.ServiceException;

import java.sql.SQLException;
import java.util.List;

public interface ShoppingService {

    public List<ShoppingList> findAll(int userId) throws SQLException, ServiceException;

    List<ShoppingList> findAllActual(int userId) throws SQLException, ServiceException;

    void delete(String shoppingIdChain, int userId) throws SQLException, ServiceException;

    void add(String foodIdChain, int userId) throws SQLException, ServiceException;

    void update(int shoppingId, int number, int userId) throws SQLException, ServiceException;
}
