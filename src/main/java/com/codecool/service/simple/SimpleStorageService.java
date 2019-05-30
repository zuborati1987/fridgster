package com.codecool.service.simple;

import com.codecool.dao.FoodDao;
import com.codecool.dao.StorageDao;
import com.codecool.model.Food;
import com.codecool.model.Storage;
import com.codecool.service.FoodService;
import com.codecool.service.StorageService;
import com.codecool.service.exception.ServiceException;

import javax.sql.rowset.serial.SerialException;
import java.sql.SQLException;
import java.util.List;

public class SimpleStorageService extends AbstractService implements StorageService {

    private StorageDao storageDao;

    public SimpleStorageService(StorageDao storageDao) {
        this.storageDao = storageDao;
    }

    public List<Storage> findAll(String id) throws SQLException, ServiceException {
        return storageDao.findAll(id);
    }

    public Storage add(String name, String userId) throws SQLException, ServiceException {
        return storageDao.add(name, userId);
    }

    public void delete(String storageIdChain, int userId) throws SQLException, ServiceException {
        String[] ids = storageIdChain.split(",");
        for (String id : ids) {
            storageDao.delete(userId, fetchInt(id, "id"));
        }
    }
}
