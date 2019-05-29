package com.codecool.service;

import com.codecool.model.Food;
import com.codecool.model.Storage;
import com.codecool.service.exception.ServiceException;

import java.sql.SQLException;
import java.util.List;

public interface StorageService {

    List<Storage> findAll(String id) throws SQLException, ServiceException;
}
