package com.food.cpg.purchaseorder;

import com.food.cpg.databasepersistence.PersistenceFactory;

public abstract class PurchaseOrderStatus {
    enum Status {
        OPEN, PLACED, RECEIVED, PAID
    }

    protected Status orderStatus;

    public Status getOrderStatus() {
        return this.orderStatus;
    }

    public abstract void moveOrder(String orderNumber);

    protected IPurchaseOrderPersistence getPersistence() {
        PersistenceFactory persistenceFactory = PersistenceFactory.getPersistenceFactory();
        return persistenceFactory.getPurchaseOrderPersistence();
    }
}
