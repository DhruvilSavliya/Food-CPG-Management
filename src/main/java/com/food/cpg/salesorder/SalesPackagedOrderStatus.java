package com.food.cpg.salesorder;

import com.food.cpg.databasepersistence.PersistenceFactory;
import com.food.cpg.inventory.IItemInventory;
import com.food.cpg.inventory.InventoryFactory;
import com.food.cpg.packaging.IPackage;
import com.food.cpg.packaging.IPackagePersistence;
import com.food.cpg.packaging.PackageFactory;

public class SalesPackagedOrderStatus extends SalesOrderStatus {

    public SalesPackagedOrderStatus() {
        this.orderStatus = Status.PACKAGED;
    }

    @Override
    public void moveOrder(SalesOrder salesOrder) {
        getPersistence().changeStatus(salesOrder.getOrderNumber(), Status.SHIPPED.name());
        decreaseItemQuantity(salesOrder);
    }


    public void decreaseItemQuantity(SalesOrder salesOrder) {
        IItemInventory itemInventory = InventoryFactory.instance().makeItemInventory();
        Integer itemID = salesOrder.getItemId();
        Integer packageId = salesOrder.getPackageId();

        IPackage packages = PackageFactory.instance().makePackage();
        packages.setPackageId(packageId);

        packages.load();

        itemInventory.setItemId(itemID);
        itemInventory.setItemQuantity(packages.getQuantity());
        itemInventory.decreaseQuantity();
    }

}