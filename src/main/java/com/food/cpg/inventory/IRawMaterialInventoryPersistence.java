package com.food.cpg.inventory;

import com.food.cpg.rawmaterial.RawMaterial;

import java.util.List;

public interface IRawMaterialInventoryPersistence {

    List<RawMaterialInventory> getDefaulter();
}
