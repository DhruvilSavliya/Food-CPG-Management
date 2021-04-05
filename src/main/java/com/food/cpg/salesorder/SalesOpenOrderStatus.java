package com.food.cpg.salesorder;

public class SalesOpenOrderStatus extends SalesOrderStatus {

    public SalesOpenOrderStatus() {
        this.orderStatus = Status.OPEN;
    }

    @Override
    public void moveOrder(SalesOrder salesOrder) {
        getPersistence().changeStatus(salesOrder.getOrderNumber(), Status.PACKAGED.name());
    }
}
