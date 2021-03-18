package com.food.cpg.dao;

import com.food.cpg.models.PurchaseOrder;

import java.util.List;
/**
 * @author P.M
 */

public interface IPurchaseOrderDAO {

    List<PurchaseOrder> getPurchaseOrderList(int manufacturerId);
    List<PurchaseOrder> getPlacedOrderList(int manufacturerId);
    List<PurchaseOrder> getReceivedOrderList(int manufacturerId);
    void placePurchaseOrder(String orderid);
    void receivePurchaseOrder(String orderid);
    void deletePurchaseOrder(String orderid);
    void reorderPurchaseOrder(String orderid);
}
