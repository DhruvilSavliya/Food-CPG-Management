package com.food.cpg.salesorder;

public class SalesPackagedOrderStatus extends SalesOrderStatus {

    private static final String PACKAGED = "Packaged";

    public SalesPackagedOrderStatus() {
        setOrderStatus();
    }

    @Override
    public void setOrderStatus() {
        this.orderStatus = PACKAGED;
    }

    @Override
    public void moveOrder() {

    }
}
