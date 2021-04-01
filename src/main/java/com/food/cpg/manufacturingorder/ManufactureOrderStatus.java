package com.food.cpg.manufacturingorder;

import com.food.cpg.databasepersistence.PersistenceFactory;

public abstract class ManufactureOrderStatus {

    enum Status {
        OPEN, MANUFACTURED, PACKAGED, STORED
    }

    protected ManufactureOrderStatus.Status orderStatus;

    public ManufactureOrderStatus.Status getOrderStatus() {
        return this.orderStatus;
    }

    public abstract void moveOrder(String orderNumber);

    protected IManufactureOrderPersistence getPersistence() {
        PersistenceFactory persistenceFactory = PersistenceFactory.getPersistenceFactory();
        return persistenceFactory.getManufactureOrderPersistence();
    }

}
