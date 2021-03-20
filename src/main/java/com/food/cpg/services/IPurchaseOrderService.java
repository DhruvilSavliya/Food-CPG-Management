package com.food.cpg.services;

import java.util.List;

import com.food.cpg.models.PurchaseOrder;

public interface IPurchaseOrderService {
    List<PurchaseOrder> getAll(int manufacturerId);

    void save(PurchaseOrder purchaseOrder);

    void delete(String purchaseOrderNumber);
}
