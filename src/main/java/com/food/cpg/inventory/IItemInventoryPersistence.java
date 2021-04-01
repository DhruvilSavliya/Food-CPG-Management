package com.food.cpg.inventory;

import java.util.List;

public interface IItemInventoryPersistence {

    List<ItemInventory> getAll(int manufacturerId);

    void increaseQuantity(ItemInventory itemInventory);

    void decreaseQuantity(ItemInventory itemInventory);
}