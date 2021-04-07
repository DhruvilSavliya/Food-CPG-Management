package com.food.cpg.inventory;

import java.util.List;

public interface IRawMaterialInventoryPersistence {

    List<IRawMaterialInventory> getDefaulter();

    List<IRawMaterialInventory> getAll(int manufacturerId);

    void increaseQuantity(IRawMaterialInventory rawMaterialInventory);

    void decreaseQuantity(IRawMaterialInventory rawMaterialInventory);
}

