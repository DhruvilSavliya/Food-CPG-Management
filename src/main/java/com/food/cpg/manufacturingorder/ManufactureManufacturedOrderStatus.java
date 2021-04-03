package com.food.cpg.manufacturingorder;

public class ManufactureManufacturedOrderStatus extends ManufactureOrderStatus {

    public ManufactureManufacturedOrderStatus() {
        this.orderStatus = Status.MANUFACTURED;
    }

    @Override
    public void moveOrder(String orderNumber) {
        getPersistence().changeStatus(orderNumber, Status.PACKAGED.name());
    }


}
