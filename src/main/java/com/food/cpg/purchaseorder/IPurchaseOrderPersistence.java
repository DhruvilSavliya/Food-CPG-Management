package com.food.cpg.purchaseorder;

import java.util.List;

public interface IPurchaseOrderPersistence {

    void save(PurchaseOrder purchaseOrder);

    public List<PurchaseOrderRawMaterial> getPurchaseOrderItemRawMaterial(int  itemId);
}