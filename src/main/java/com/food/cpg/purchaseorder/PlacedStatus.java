package com.food.cpg.purchaseorder;

import com.food.cpg.databasepersistence.PersistenceFactory;

public class PlacedStatus extends PurchaseOrderStatus{

    private static final String PLACED_STATUS ="Placed";
    private IPurchaseOrderPersistence getPersistence() {
        PersistenceFactory persistenceFactory = PersistenceFactory.getPersistenceFactory();
        return persistenceFactory.getPurchaseOrderPersistence();
    }
    public void setOrderStatus()
    {
        this.orderStatus = PLACED_STATUS;
    }

    public void moveOrder(String purchaseOrderNumber) {
        getPersistence().moveToReceivedOrder(purchaseOrderNumber);

    }
}
