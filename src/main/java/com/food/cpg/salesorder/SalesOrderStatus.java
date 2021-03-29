package com.food.cpg.salesorder;

public abstract class SalesOrderStatus {
    protected String orderStatus;

    public String getOrderStatus() {
        return this.orderStatus;
    }

    public abstract void setOrderStatus();

    public abstract void moveOrder();
}
