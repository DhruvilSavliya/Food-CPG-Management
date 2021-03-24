package com.food.cpg.manufacturer;

public interface IManufacturerPersistence {

    Manufacturer get(String manufacturerEmail);

    void load(Manufacturer manufacturer);

    void register(Manufacturer manufacturer);

    void createLoginAccount(Manufacturer manufacturer);
}