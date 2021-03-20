package com.food.cpg.services;

import java.util.List;

import com.food.cpg.models.PurchaseOrder;

public interface IPurchaseOrderService {
    List<PurchaseOrder> getPurchaseOrder(int manufacturerId);
    List<PurchaseOrder> getPlacedOrder(int manufacturerId);
    List<PurchaseOrder> getReceivedOrder(int manufacturerId);

    void save(PurchaseOrder purchaseOrder);

    void delete(String purchaseOrderNumber);
}
