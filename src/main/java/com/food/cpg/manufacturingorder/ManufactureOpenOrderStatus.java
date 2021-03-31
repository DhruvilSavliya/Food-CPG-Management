package com.food.cpg.manufacturingorder;

public class ManufactureOpenOrderStatus extends ManufactureOrderStatus {

    public ManufactureOpenOrderStatus(){
        this.orderStatus = Status.OPEN;
    }

    @Override
    public void moveOrder(String orderNumber) {
        getPersistence().changeStatus(orderNumber, Status.MANUFACTURED.name());
    }
}
