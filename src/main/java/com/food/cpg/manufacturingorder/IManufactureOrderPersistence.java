package com.food.cpg.manufacturingorder;

import com.food.cpg.item.Item;

import java.util.List;

public interface IManufactureOrderPersistence {

    List<ManufactureOrder> getAll(int manufacturerId);

    void load(ManufactureOrder manufactureOrder);

    void save(ManufactureOrder manufactureOrder);

    void delete(int orderNumber);

}
