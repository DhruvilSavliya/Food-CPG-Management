package com.food.cpg.item;

import java.util.List;

public interface IItemPersistence {

    List<Item> getAll(int manufacturerId);

    void load(Item item);

    Integer save(Item item);

    void delete(int itemId);

}
