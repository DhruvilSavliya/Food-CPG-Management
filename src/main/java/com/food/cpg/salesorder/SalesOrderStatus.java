package com.food.cpg.salesorder;

import com.food.cpg.databasepersistence.PersistenceFactory;

public abstract class SalesOrderStatus {

    enum Status {
        OPEN, PACKAGED, SHIPPED, PAID
    }

    protected SalesOrderStatus.Status orderStatus;

    public SalesOrderStatus.Status getOrderStatus() {
        return this.orderStatus;
    }

    public abstract void moveOrder(SalesOrder salesOrder);

    protected ISalesOrderPersistence getPersistence() {
        PersistenceFactory persistenceFactory = PersistenceFactory.getPersistenceFactory();
        return persistenceFactory.getSalesOrderPersistence();
    }
}
