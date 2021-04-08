package com.food.cpg.manufactureorder;

import java.util.List;

public interface IManufactureOrderPersistence {

    List<ManufactureOrder> getAllOpenOrders(int manufacturerId);

    List<ManufactureOrder> getAllManufacturedOrders(int manufacturerId);

    List<ManufactureOrder> getAllPackagedOrders(int manufacturerId);

    List<ManufactureOrder> getAllOrders(int manufacturerId, String orderStatus);

    void load(ManufactureOrder manufactureOrder);

    void save(ManufactureOrder manufactureOrder);

    void delete(String orderNumber);

    Double loadItemCost(int itemId);

    void changeStatus(String orderNumber, String orderStatus);

}
