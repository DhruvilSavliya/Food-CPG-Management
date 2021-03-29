package com.food.cpg.inventory;

import java.util.List;

public interface IRawMaterialInventoryPersistence {

    List<RawMaterialInventory> getDefaulter();

    List<RawMaterialInventory> getAll(int manufacturerId);

}
