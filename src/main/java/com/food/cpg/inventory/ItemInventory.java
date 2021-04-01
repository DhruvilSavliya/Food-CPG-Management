package com.food.cpg.inventory;

import com.food.cpg.databasepersistence.PersistenceFactory;

public class ItemInventory {
    private Integer itemId;
    private Integer itemQuantity;

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(Integer itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    private IItemInventoryPersistence getPersistence() {
        PersistenceFactory persistenceFactory = PersistenceFactory.getPersistenceFactory();
        return persistenceFactory.getItemInventoryPersistence();
    }


    public void increaseQuantity() {
        getPersistence().increaseQuantity(this);
    }

    public void decreaseQuantity() {
        getPersistence().decreaseQuantity(this);
    }


}
