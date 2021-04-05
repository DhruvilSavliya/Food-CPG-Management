package com.food.cpg.manufacturingorder;

import com.food.cpg.inventory.ItemInventory;

public class ManufacturePackagedOrderStatus extends ManufactureOrderStatus{

    public ManufacturePackagedOrderStatus(){
        this.orderStatus = Status.PACKAGED;
    }

    @Override
    public void moveOrder(ManufactureOrder manufactureOrder) {
        String orderNumber = manufactureOrder.getOrderNumber();

        getPersistence().changeStatus(orderNumber, Status.STORED.name());
    }

}
