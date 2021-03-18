package com.food.cpg.dao;

import java.util.List;

import com.food.cpg.models.PurchaseOrderRawMaterial;

public interface IPurchaseOrderRawMaterialDAO {
    void save(List<PurchaseOrderRawMaterial> purchaseOrderRawMaterials);
}