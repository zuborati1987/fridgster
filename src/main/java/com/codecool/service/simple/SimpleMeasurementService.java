package com.codecool.service.simple;

import com.codecool.dao.MeasurementDao;
import com.codecool.dao.StorageDao;
import com.codecool.model.Measurement;
import com.codecool.model.Storage;
import com.codecool.service.MeasurementService;
import com.codecool.service.StorageService;
import com.codecool.service.exception.ServiceException;

import java.sql.SQLException;
import java.util.List;

public class SimpleMeasurementService extends AbstractService implements MeasurementService {

    private MeasurementDao measurementDao;

    public SimpleMeasurementService(MeasurementDao measurementDao) {
        this.measurementDao = measurementDao;
    }

    public int add(String name, int userId) throws SQLException, ServiceException {
        return measurementDao.add(name, userId);
    }

    public int findIdByName(String name, int userId) throws SQLException, ServiceException {
        return measurementDao.findIdByName(name, userId);
    }
    
}
