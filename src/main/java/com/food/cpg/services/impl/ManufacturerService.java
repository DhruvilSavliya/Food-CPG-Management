package com.food.cpg.services.impl;

import java.util.List;

import com.food.cpg.dao.IManufacturerDAO;
import com.food.cpg.dao.impl.ManufacutrerDAOImpl;
import com.food.cpg.models.Manufacturer;
import com.food.cpg.services.ImanufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;




/**
 * @author Dhruvilkumar Savliya
 */
@Service
public class ManufacturerService implements ImanufacturerService
{

    private final IManufacturerDAO manufacturerDAO;

    @Autowired
    public ManufacturerService(ManufacutrerDAOImpl manufacturerDAO) {
        this.manufacturerDAO = manufacturerDAO;
    }



    @Override
    public List<Manufacturer> getManufacturerList(int manufacturerId) {
        return manufacturerDAO.getManufacturerList(manufacturerId);
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