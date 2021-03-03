package com.food.cpg.services;

import java.util.List;

import com.food.cpg.models.Vendor;

/**
 * @author Kartik Gevariya
 */
public interface IVendorService {

    List<Vendor> getVendorsList(int manufacturerId);

    void saveVendor(Vendor vendor);

    Vendor getVendor(int vendorId);

    void updateVendor(Vendor vendor);

    void deleteVendor(int vendorId);
}
