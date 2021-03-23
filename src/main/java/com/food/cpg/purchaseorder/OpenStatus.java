package com.food.cpg.purchaseorder;

import com.food.cpg.databasepersistence.PersistenceFactory;



public class OpenStatus extends PurchaseOrderStatus{

    private static final String OPEN_STATUS ="Open";

    private IPurchaseOrderPersistence getPersistence() {
        PersistenceFactory persistenceFactory = PersistenceFactory.getPersistenceFactory();
        return persistenceFactory.getPurchaseOrderPersistence();
    }

    public void setOrderStatus()
    {
        this.orderStatus = OPEN_STATUS;
    }

    public void moveOrder(String purchaseOrderNumber) {

       getPersistence().moveToPlacedOrder(purchaseOrderNumber);

    }

}
