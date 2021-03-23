package com.food.cpg.purchaseorder;

import java.util.List;

public interface IPurchaseOrderPersistence {

    void save(PurchaseOrder purchaseOrder);
    void delete(PurchaseOrder purchaseOrder);
    void moveToPlacedOrder(String purchaseOrderNumber);
    void moveToReceivedOrder(String purchaseOrderNumber);
    public List<PurchaseOrder> getOpenPurchaseOrder(int manufacturerId);
    public List<PurchaseOrder> getPlacedPurchaseOrder(int manufacturerId);
    public List<PurchaseOrder> getReceivedPurchaseOrder(int manufacturerId);
}