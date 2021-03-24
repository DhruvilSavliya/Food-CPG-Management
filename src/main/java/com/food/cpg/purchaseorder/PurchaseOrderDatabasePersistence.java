package com.food.cpg.purchaseorder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.food.cpg.databasepersistence.ICommonDatabaseOperation;
import com.food.cpg.exceptions.ServiceException;

public class PurchaseOrderDatabasePersistence implements IPurchaseOrderPersistence {

    private final ICommonDatabaseOperation commonDatabaseOperation;

    public PurchaseOrderDatabasePersistence(ICommonDatabaseOperation commonDatabaseOperation) {
        this.commonDatabaseOperation = commonDatabaseOperation;
    }

    @Override
    public void save(PurchaseOrder purchaseOrder) {
        String sql = "insert into purchase_orders (order_number, vendor_id, total_cost, manufacturer_id) values (?, ?, ?, ?)";
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(purchaseOrder.getOrderNumber());
        placeholderValues.add(purchaseOrder.getVendorId());
        placeholderValues.add(purchaseOrder.getTotalCost());
        placeholderValues.add(purchaseOrder.getManufacturerId());

        try {
            commonDatabaseOperation.executeUpdate(sql, placeholderValues);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }
}
