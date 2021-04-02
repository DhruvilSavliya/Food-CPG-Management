package com.food.cpg.manufacturingorder;

import com.food.cpg.inventory.ItemInventory;

public class ManufactureManufacturedOrderStatus extends ManufactureOrderStatus {

    public ManufactureManufacturedOrderStatus() {
        this.orderStatus = Status.MANUFACTURED;
    }

    @Override
    public void moveOrder(ManufactureOrder manufactureOrder) {
        String orderNumber = manufactureOrder.getOrderNumber();

        ItemInventory itemInventory = getItemInventoryInstance();

        Integer itemID = manufactureOrder.getItemId();
        Double itemQuantity = manufactureOrder.getItemQuantity();

        itemInventory.setItemId(itemID);
        itemInventory.setItemQuantity(itemQuantity);
        itemInventory.increaseQuantity();

        getPersistence().changeStatus(orderNumber, Status.PACKAGED.name());
    }

    private ItemInventory getItemInventoryInstance(){
        ItemInventory itemInventory = new ItemInventory();
        return itemInventory;
    }

}
