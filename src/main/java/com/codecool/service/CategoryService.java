package com.codecool.service;

import com.codecool.model.Category;
import com.codecool.model.Storage;
import com.codecool.service.exception.ServiceException;

import java.sql.SQLException;
import java.util.List;

public interface CategoryService {

    int add(String name, int userId) throws SQLException, ServiceException;

    int findIdByName(String name, int userId) throws SQLException, ServiceException;
}
