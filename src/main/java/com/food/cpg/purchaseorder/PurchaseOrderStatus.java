package com.food.cpg.purchaseorder;

public abstract class PurchaseOrderStatus {
    public String orderStatus;

    public String  getOrderStatus()
    {
        return orderStatus;
    }

    abstract void setOrderStatus();

    abstract void moveOrder(String purchaseOrderNumber);

}
