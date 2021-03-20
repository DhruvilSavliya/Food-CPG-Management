package com.food.cpg.dao;

import com.food.cpg.models.PurchaseOrder;

import java.util.List;

public interface IPurchaseOrderDAO {
    void save(PurchaseOrder purchaseOrder);

    public List<PurchaseOrder> getPurchaseOrder(int manufacturerId);
    public List<PurchaseOrder> getPlacedOrder(int manufacturerId);
    public List<PurchaseOrder> getReceivedOrder(int manufacturerId);
    void moveToPlacedOrder(String orderNumber);
    void moveToReceivedOrder(String orderNumber);
    void delete(String orderNumber);


}