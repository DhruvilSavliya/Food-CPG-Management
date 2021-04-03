package com.food.cpg.purchaseorder;

public class PurchaseOpenOrderStatus extends PurchaseOrderStatus {

    public PurchaseOpenOrderStatus() {
        this.orderStatus = Status.OPEN;
    }

    @Override
    public void moveOrder(String orderNumber) {
        getPersistence().changeStatus(orderNumber, Status.PLACED.name());
    }
}
