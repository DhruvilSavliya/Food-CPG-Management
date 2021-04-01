package com.food.cpg.inventory;

import com.food.cpg.databasepersistence.ICommonDatabaseOperation;
import com.food.cpg.exceptions.ServiceException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemInventoryDatabasePersistence implements IItemInventoryPersistence {

    private final ICommonDatabaseOperation commonDatabaseOperation;

    public ItemInventoryDatabasePersistence(ICommonDatabaseOperation commonDatabaseOperation) {
        this.commonDatabaseOperation = commonDatabaseOperation;
    }

    @Override
    public void increaseQuantity(ItemInventory itemInventory) {
        String sql = "update item_inventory set quantity = quantity + ? where item_id = ?";
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(itemInventory.getItemQuantity());
        placeholderValues.add(itemInventory.getItemId());

        try {
            commonDatabaseOperation.executeUpdate(sql, placeholderValues);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void decreaseQuantity(ItemInventory itemInventory) {
        String sql = "update item_inventory set quantity = quantity - ? where item_id = ?";
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(itemInventory.getItemQuantity());
        placeholderValues.add(itemInventory.getItemId());

        try {
            commonDatabaseOperation.executeUpdate(sql, placeholderValues);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }
}
