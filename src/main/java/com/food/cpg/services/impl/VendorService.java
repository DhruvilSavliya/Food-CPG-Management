package com.food.cpg.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.food.cpg.dao.IVendorDAO;
import com.food.cpg.dao.impl.VendorDAOImpl;
import com.food.cpg.models.Vendor;
import com.food.cpg.services.IVendorService;

/**
 * @author Kartik Gevariya
 */
@Service
public class VendorService implements IVendorService {

    private final IVendorDAO vendorDAO;

    @Autowired
    public VendorService(VendorDAOImpl vendorDAO) {
        this.vendorDAO = vendorDAO;
    }

    @Override
    public List<Vendor> getVendorsList(int manufacturerId) {
        return vendorDAO.getVendorsList(manufacturerId);
    }

    @Override
    public void saveVendor(Vendor vendor) {
        vendorDAO.saveVendor(vendor);
    }

    @Override
    public Vendor getVendor(int vendorId) {
        return vendorDAO.getVendor(vendorId);
    }

    @Override
    public void updateVendor(Vendor vendor) {
        vendorDAO.updateVendor(vendor);
    }

    @Override
    public void deleteVendor(int vendorId) {
        vendorDAO.deleteVendor(vendorId);
    }
}
