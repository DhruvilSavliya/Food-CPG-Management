package com.food.cpg.vendor;

import java.util.List;

public interface IVendorPersistence {

    List<Vendor> getAll(int manufacturerId);

    void load(Vendor vendor);

    void save(Vendor vendor);

    void update(Vendor vendor);

    void delete(int vendorId);
}