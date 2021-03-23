package com.food.cpg.manufacturingorder;

import com.food.cpg.databasepersistence.ICommonDatabaseOperation;
import com.food.cpg.exceptions.ServiceException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManufactureOrderDatabasePersistence implements IManufactureOrderPersistence{

    private final ICommonDatabaseOperation commonDatabaseOperation;

    public ManufactureOrderDatabasePersistence(ICommonDatabaseOperation commonDatabaseOperation) {
        this.commonDatabaseOperation = commonDatabaseOperation;
    }

    @Override
    public List<ManufactureOrder> getAll(int manufacturerId) {
        return null;
    }

    @Override
    public void load(ManufactureOrder manufactureOrder) {

    }

    @Override
    public void save(ManufactureOrder manufactureOrder) {

        String sql = "insert into manufacture_orders (order_number, order_status, manufacturing_cost, tax, cost, manufacturer_id) values (?, ?, ?, ?, ?, ?)";
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(manufactureOrder.getOrderNumber());
        placeholderValues.add(manufactureOrder.getOrderStatus());
        placeholderValues.add(manufactureOrder.getManufacturingCost());
        placeholderValues.add(manufactureOrder.getTax());
        placeholderValues.add(manufactureOrder.getCost());
        placeholderValues.add(manufactureOrder.getManufacturerId());

        try {
            commonDatabaseOperation.executeUpdate(sql, placeholderValues);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void delete(int orderNumber) {

    }
}
