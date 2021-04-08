package com.food.cpg.rawmaterial;

import com.food.cpg.manufacturer.IManufacturer;
import com.food.cpg.manufacturer.IManufacturerPersistence;
import com.food.cpg.manufacturer.ManufacturerFactory;

public abstract class RawMaterialFactory {

    private static RawMaterialFactory rawMaterialFactory;

    public static RawMaterialFactory instance() {
        return rawMaterialFactory;
    }

    public static void setRawMaterialFactory(RawMaterialFactory factory) {
        rawMaterialFactory = factory;
    }

    public abstract IRawMaterial makeRawMaterial();

    public abstract IRawMaterialPersistence makeRawMaterialPersistence();
}
