package com.food.cpg.purchaseorder;


public interface IPurchaseOrderRawMaterialPersistence {

    void save(PurchaseOrderRawMaterial purchaseOrderRawMaterials);
    void delete(String PurchaseOrderNumber);
}