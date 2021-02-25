package com.food.cpg.services.impl;

import com.food.cpg.dao.IManufacturerDAO;
import com.food.cpg.dao.impl.ManufacturerDAOImpl;
import com.food.cpg.models.Manufacturer;
import com.food.cpg.services.IManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManufacturerService implements IManufacturerService {

    private final IManufacturerDAO manufacturerDAO;

    @Autowired
    public ManufacturerService(ManufacturerDAOImpl manufacturerDAO) {
        this.manufacturerDAO = manufacturerDAO;

    }

    @Override
    public void saveManufacturer(Manufacturer manufacturer) {
        manufacturerDAO.saveManufacturer(manufacturer);
    }
}