package com.food.cpg.services;

import com.food.cpg.models.Item;
import com.food.cpg.models.ItemIngredient;
import com.food.cpg.models.RawMaterial;

import java.util.List;

public interface IItemService {

    List<Item> getItemsList(int manufacturerId);

    void saveItem(Item item );

    Item getItem(int itemId);

    void updateItem(Item item);

    void deleteItem(int itemId);

    Double calculateTotal(Item item);
}
