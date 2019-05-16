package com.codecool.dao;

import com.codecool.model.Measurement;

import java.sql.SQLException;
import java.util.List;

public interface MeasurementDao {

    List<Measurement> findAll() throws SQLException;

    Measurement findById(int id) throws SQLException;

    Measurement add(String name, int userId) throws SQLException;

    void delete(String name, int userId) throws SQLException;


}
