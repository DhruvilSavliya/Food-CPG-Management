package com.food.cpg.purchaseorder;

public class PurchaseReceivedOrderStatus extends PurchaseOrderStatus {

    public PurchaseReceivedOrderStatus() {
        this.orderStatus = Status.RECEIVED;
    }

    @Override
    public void moveOrder(PurchaseOrder purchaseOrder) {
        getPersistence().changeStatus(purchaseOrder.getOrderNumber(), Status.PAID.name());
    }
}
