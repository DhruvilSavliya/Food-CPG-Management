package com.food.cpg.salesorder;

public class SalesPackagedOrderStatus extends SalesOrderStatus {

    public SalesPackagedOrderStatus() {
        this.orderStatus = Status.PACKAGED;
    }

    @Override
    public void moveOrder(String orderNumber) {
        getPersistence().changeStatus(orderNumber, Status.SHIPPED.name());
    }
}
