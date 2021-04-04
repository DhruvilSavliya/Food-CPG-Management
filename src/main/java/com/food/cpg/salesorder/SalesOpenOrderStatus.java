package com.food.cpg.salesorder;

public class SalesOpenOrderStatus extends SalesOrderStatus {

    public SalesOpenOrderStatus() {
        this.orderStatus = Status.OPEN;
    }

    @Override
    public void moveOrder(String orderNumber) {
        getPersistence().changeStatus(orderNumber, Status.PACKAGED.name());
    }
}
