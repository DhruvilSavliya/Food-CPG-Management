package com.food.cpg.inventory;

import com.food.cpg.databasepersistence.PersistenceFactory;

public class DefaultInventoryFactory extends InventoryFactory {

    private final IRawMaterialInventoryPersistence rawMaterialInventoryPersistence;

    public DefaultInventoryFactory(PersistenceFactory persistenceFactory) {
        rawMaterialInventoryPersistence = persistenceFactory.getRawMaterialInventoryPersistence();
    }

    @Override
    public IRawMaterialInventory makeRawMaterialInventory() {
        return new RawMaterialInventory();
    }

    @Override
    public IRawMaterialInventoryPersistence makeRawMaterialInventoryPersistence() {
        return rawMaterialInventoryPersistence;
    }
}
