package com.food.cpg.salesorder;

public class SalesOpenOrderStatus extends SalesOrderStatus {

    private static final String OPEN = "Open";

    public SalesOpenOrderStatus() {
        setOrderStatus();
    }

    @Override
    public void setOrderStatus() {
        this.orderStatus = OPEN;
    }

    @Override
    public void moveOrder() {

    }
}
