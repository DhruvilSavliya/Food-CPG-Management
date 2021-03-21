package com.food.cpg.services;

import java.util.List;

import com.food.cpg.models.Vendor;

/**
 * @author Kartik Gevariya
 */
public interface IVendorService {

    List<Vendor> getVendorsList(int manufacturerId);
}
