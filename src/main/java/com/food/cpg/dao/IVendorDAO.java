package com.food.cpg.dao;

import java.util.List;

import com.food.cpg.models.Vendor;

/**
 * Vendor DAO interface
 *
 * @author Kartik Gevariya
 */
public interface IVendorDAO {

    List<Vendor> getVendorsList(int manufacturerId);

    Vendor getVendor(int vendorId);

    void saveVendor(Vendor vendor);

    void updateVendor(Vendor vendor);

    void deleteVendor(int vendorId);
}