package com.food.cpg.services.impl;

import java.util.List;

import com.food.cpg.dao.IManufacturerDAO;
import com.food.cpg.dao.impl.ManufacturerDAOImpl;
import com.food.cpg.models.Manufacturer;
import com.food.cpg.services.IManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;




/**
 * @author Dhruvilkumar Savliya
 */
@Service
public class ManufacturerService implements IManufacturerService
{

    private final IManufacturerDAO manufacturerDAO;

    @Autowired
    public ManufacturerService(ManufacturerDAOImpl manufacturerDAO) {
        this.manufacturerDAO = manufacturerDAO;
    }



    @Override
    public List<Manufacturer> getManufacturerList(int manufacturerId) {
        return manufacturerDAO.getManufacturerList(manufacturerId);
    }

    @Override
    public void saveManufacturer(Manufacturer manufacturer) {
        manufacturerDAO.saveManufacturer(manufacturer);
    }


    @Override
    public Manufacturer getManufacturer(int manufacturerId) {
        return manufacturerDAO.getManufacturer(manufacturerId);
    }

    @Override
    public void approveManufacturer(int manufacturerId) { manufacturerDAO.approveManufacturer(manufacturerId); }

    @Override
    public void blockManufacturer(int manufacturerId) {
        manufacturerDAO.blockManufacturer(manufacturerId);
    }


}
