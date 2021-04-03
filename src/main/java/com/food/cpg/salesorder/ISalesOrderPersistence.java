package com.food.cpg.salesorder;

import java.util.List;

public interface ISalesOrderPersistence {

    List<SalesOrder> getAllOpenOrders(int manufacturerId);

    List<SalesOrder> getAllPackagedOrders(int manufacturerId);

    List<SalesOrder> getAllShippedOrders(int manufacturerId);

    List<SalesOrder> getAllPaidOrders(int manufacturerId);

    void load(SalesOrder salesOrder);

    void save(SalesOrder salesOrder);

    void delete(String orderNumber);

    void changeStatus(String orderNumber, String orderStatus);
}