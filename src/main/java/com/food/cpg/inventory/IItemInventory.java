package com.food.cpg.inventory;

import com.food.cpg.authentication.AuthenticationSessionDetails;
import com.food.cpg.databasepersistence.PersistenceFactory;

import java.util.List;

public interface IItemInventory {
    public Integer getItemId();

    public void setItemId(Integer itemId);

    public String getItemName();

    public void setItemName(String itemName);

    public Double getItemQuantity();

    public void setItemQuantity(Double itemQuantity);

    public Integer getManufacturerId();

    public void setManufacturerId(Integer manufacturerId);

    public List<ItemInventory> getAll();

    public void save();

    public void increaseQuantity();

    public void decreaseQuantity();
}
