package com.food.cpg.manufacturer;

import java.util.List;

public interface IManufacturerPersistence {

    List<Manufacturer> getAll();

    Manufacturer get(String manufacturerEmail);

    void load(Manufacturer manufacturer);

    void register(Manufacturer manufacturer);

    void createLoginAccount(Manufacturer manufacturer);
}