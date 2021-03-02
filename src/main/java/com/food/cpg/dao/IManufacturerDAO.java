package com.food.cpg.dao;

import java.util.List;

import com.food.cpg.models.Manufacturer;

/**
 * Manufacturer DAO interface
 *
 * @author Dhruvilkumar Savliya
 * @author Rotesh Chhabra
 */
public interface IManufacturerDAO {

    List<Manufacturer> getManufacturerList(int manufacturerId);

    Manufacturer getManufacturer(int manufacturerId);

    void approveManufacturer(int manufacturerId);

    void blockManufacturer(int manufacturerId);

    void saveManufacturer(Manufacturer manufacturer);

}
