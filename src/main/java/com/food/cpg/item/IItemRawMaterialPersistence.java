package com.food.cpg.item;

public interface IItemRawMaterialPersistence {

    void save(ItemRawMaterial itemRawMaterial);

    void delete(int itemId);

    Double loadUnitCost(Integer rawMaterialId);
}
