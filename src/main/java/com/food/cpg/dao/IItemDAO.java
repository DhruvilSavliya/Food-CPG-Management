package com.food.cpg.dao;

import com.food.cpg.models.Item;
import com.food.cpg.models.RawMaterial;

import java.util.List;

public interface IItemDAO {

    List<Item> getItemsList(int manufacturerId);

    Item getItem(int itemId);

    void saveItem(Item item);

    void updateItem(Item item);

    void deleteItem(int itemId);
}

