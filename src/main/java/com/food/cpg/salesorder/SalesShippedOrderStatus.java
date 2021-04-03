package com.food.cpg.salesorder;

public class SalesShippedOrderStatus extends SalesOrderStatus {

    public SalesShippedOrderStatus() {
        this.orderStatus = Status.SHIPPED;
    }

    @Override
    public void moveOrder(String orderNumber) {
        getPersistence().changeStatus(orderNumber, Status.PAID.name());
    }
}
