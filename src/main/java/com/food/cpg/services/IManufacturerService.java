package com.food.cpg.services;

import java.util.List;

import com.food.cpg.models.Manufacturer;

/**
 * @author Dhruvilkumar Savliya
 */
public interface IManufacturerService {

    List<Manufacturer> getManufacturerList(int manufacturerId);

    void saveManufacturer(Manufacturer manufacturer);

    void approveManufacturer(int manufacturerId);

    Manufacturer getManufacturer(int manufacturerId);

    void blockManufacturer(int manufacturerId);

}
