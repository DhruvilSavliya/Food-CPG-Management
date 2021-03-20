package com.food.cpg.PurchaseOrder;

public abstract class PurchaseOrderStatus {
    public String orderStatus;

    abstract void moveOrder(String purchaseOrderNumber);
}
