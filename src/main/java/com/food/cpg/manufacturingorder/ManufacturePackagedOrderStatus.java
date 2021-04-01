package com.food.cpg.manufacturingorder;

public class ManufacturePackagedOrderStatus extends ManufactureOrderStatus{

    public ManufacturePackagedOrderStatus(){
        this.orderStatus = Status.PACKAGED;
    }

    @Override
    public void moveOrder(String orderNumber) {
        getPersistence().changeStatus(orderNumber, Status.STORED.name());
    }
}
