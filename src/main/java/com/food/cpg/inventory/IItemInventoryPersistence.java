package com.food.cpg.inventory;

public interface IItemInventoryPersistence {

    void increaseQuantity(ItemInventory itemInventory);

    void decreaseQuantity(ItemInventory itemInventory);
}
