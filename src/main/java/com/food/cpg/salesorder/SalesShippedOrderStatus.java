package com.food.cpg.salesorder;

public class SalesShippedOrderStatus extends SalesOrderStatus {

    private static final String SHIPPED = "Shipped";

    public SalesShippedOrderStatus() {
        setOrderStatus();
    }

    @Override
    public void setOrderStatus() {
        this.orderStatus = SHIPPED;
    }

    @Override
    public void moveOrder() {

    }
}
