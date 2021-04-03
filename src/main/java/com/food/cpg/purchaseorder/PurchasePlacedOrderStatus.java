package com.food.cpg.purchaseorder;

public class PurchasePlacedOrderStatus extends PurchaseOrderStatus {

    public PurchasePlacedOrderStatus() {
        this.orderStatus = Status.PLACED;
    }

    @Override
    public void moveOrder(String orderNumber) {
        getPersistence().changeStatus(orderNumber, Status.RECEIVED.name());
    }
}
