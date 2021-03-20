package com.food.cpg.manufacturer.registration;

import java.util.List;

import com.food.cpg.databasepersistence.PersistenceFactory;
import com.food.cpg.manufacturer.Manufacturer;

public class Registration {

    private Manufacturer manufacturer;
    private String status;

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Registration> getAll() {
        return getPersistence().getAll();
    }

    public void approve(String email) {
        getPersistence().approve(email);
    }

    public void block(String email) {
        getPersistence().block(email);
    }

    private IRegistrationPersistence getPersistence() {
        PersistenceFactory persistenceFactory = PersistenceFactory.getPersistenceFactory();
        return persistenceFactory.getRegistrationPersistence();
    }
}
