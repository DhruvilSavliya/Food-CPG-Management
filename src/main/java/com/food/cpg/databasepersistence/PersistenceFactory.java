package com.food.cpg.databasepersistence;

import com.food.cpg.manufacturer.registration.IRegistrationPersistence;
import com.food.cpg.manufacturer.IManufacturerPersistence;
import com.food.cpg.vendor.IVendorPersistence;

public abstract class PersistenceFactory {
    public abstract IVendorPersistence getVendorPersistence();
    public abstract IManufacturerPersistence getManufacturerPersistence();
    public abstract IRegistrationPersistence getRegistrationPersistence();

    public static PersistenceFactory getPersistenceFactory() {
        return new DatabasePersistenceFactory();
    }
}
