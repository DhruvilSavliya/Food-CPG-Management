package com.food.cpg.databasepersistence;

import com.food.cpg.handlers.ApplicationBeanHandler;
import com.food.cpg.manufacturer.registration.IRegistrationPersistence;
import com.food.cpg.manufacturer.registration.RegistrationDatabasePersistence;
import com.food.cpg.manufacturer.IManufacturerPersistence;
import com.food.cpg.manufacturer.ManufacturerDatabasePersistence;
import com.food.cpg.vendor.IVendorPersistence;
import com.food.cpg.vendor.VendorDatabasePersistence;

public class DatabasePersistenceFactory extends PersistenceFactory {

    ICommonDatabaseOperation commonDatabaseOperation = ApplicationBeanHandler.getBean(ICommonDatabaseOperation.class);

    @Override
    public IVendorPersistence getVendorPersistence() {
        return new VendorDatabasePersistence(commonDatabaseOperation);
    }

    @Override
    public IManufacturerPersistence getManufacturerPersistence() {
        return new ManufacturerDatabasePersistence(commonDatabaseOperation);
    }

    @Override
    public IRegistrationPersistence getRegistrationPersistence() {
        return new RegistrationDatabasePersistence(commonDatabaseOperation);
    }
}
