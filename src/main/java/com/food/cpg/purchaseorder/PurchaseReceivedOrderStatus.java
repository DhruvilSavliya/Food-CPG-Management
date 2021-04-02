package com.food.cpg.purchaseorder;

public class PurchaseReceivedOrderStatus extends PurchaseOrderStatus{

    public PurchaseReceivedOrderStatus() {
        this.orderStatus = Status.RECEIVED;
    }

    @Override
    public void moveOrder(String orderNumber) {
        getPersistence().changeStatus(orderNumber, Status.PAID.name());
    }

}
