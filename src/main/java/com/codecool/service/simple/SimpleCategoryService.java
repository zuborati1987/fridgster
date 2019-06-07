package com.codecool.service.simple;

import com.codecool.dao.CategoryDao;
import com.codecool.dao.StorageDao;
import com.codecool.model.Category;
import com.codecool.model.Storage;
import com.codecool.service.CategoryService;
import com.codecool.service.StorageService;
import com.codecool.service.exception.ServiceException;

import java.sql.SQLException;
import java.util.List;

public class SimpleCategoryService extends AbstractService implements CategoryService {

    private CategoryDao categoryDao;

    public SimpleCategoryService(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    public int add(String name, int userId) throws SQLException, ServiceException {
        return categoryDao.add(name, userId);
    }

    public int findIdByName(String name, int userId) throws SQLException, ServiceException {
        return categoryDao.findIdByName(name, userId);
    }
}
