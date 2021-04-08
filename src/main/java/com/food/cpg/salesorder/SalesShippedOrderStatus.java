package com.food.cpg.salesorder;

public class SalesShippedOrderStatus extends SalesOrderStatus {

    public SalesShippedOrderStatus() {
        this.orderStatus = Status.SHIPPED;
    }

    @Override
    public void moveOrder(SalesOrder salesOrder) {
        getPersistence().changeStatus(salesOrder.getOrderNumber(), Status.PAID.name());
    }
}
