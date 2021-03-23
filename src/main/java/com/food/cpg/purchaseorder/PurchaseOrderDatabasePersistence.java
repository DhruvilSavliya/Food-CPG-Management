package com.food.cpg.purchaseorder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.food.cpg.databasepersistence.ICommonDatabaseOperation;
import com.food.cpg.exceptions.ServiceException;
import com.food.cpg.vendor.Vendor;

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
    @Override
    public List<PurchaseOrder> getOpenPurchaseOrder(int manufacturerId) {
        List<PurchaseOrder> openPurchaseOrders = new ArrayList<>();

        String sql = "select * from purchase_orders where manufacturer_id = ? and order_status = 'Open'";
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(manufacturerId);
        try (Connection connection = commonDatabaseOperation.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                commonDatabaseOperation.loadPlaceholderValues(preparedStatement, placeholderValues);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        PurchaseOrder purchaseOrder = new PurchaseOrder();
                        purchaseOrder.setOrderNumber(rs.getString("order_number"));
                        purchaseOrder.setTotalCost(rs.getDouble("total_cost"));
                        openPurchaseOrders.add(purchaseOrder);
                    }
                }
            }
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
        return openPurchaseOrders;
    }

    @Override
    public List<PurchaseOrder> getPlacedPurchaseOrder(int manufacturerId) {
        List<PurchaseOrder> placedPurchaseOrders = new ArrayList<>();

        String sql = "select * from purchase_orders where manufacturer_id = ? and order_status = 'Placed'";
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(manufacturerId);
        try (Connection connection = commonDatabaseOperation.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                commonDatabaseOperation.loadPlaceholderValues(preparedStatement, placeholderValues);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        PurchaseOrder purchaseOrder = new PurchaseOrder();
                        purchaseOrder.setOrderNumber(rs.getString("order_number"));
                        purchaseOrder.setTotalCost(rs.getDouble("total_cost"));
                        placedPurchaseOrders.add(purchaseOrder);
                    }
                }
            }
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
        return placedPurchaseOrders;
    }
    @Override
    public List<PurchaseOrder> getReceivedPurchaseOrder(int manufacturerId) {
        List<PurchaseOrder> receivedPurchaseOrders = new ArrayList<>();

        String sql = "select * from purchase_orders where manufacturer_id = ? and order_status = 'Received'";
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(manufacturerId);
        try (Connection connection = commonDatabaseOperation.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                commonDatabaseOperation.loadPlaceholderValues(preparedStatement, placeholderValues);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        PurchaseOrder purchaseOrder = new PurchaseOrder();
                        purchaseOrder.setOrderNumber(rs.getString("order_number"));
                        purchaseOrder.setTotalCost(rs.getDouble("total_cost"));
                        receivedPurchaseOrders.add(purchaseOrder);
                    }
                }
            }
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
        return receivedPurchaseOrders;
    }

    @Override
    public void moveToPlacedOrder(String purchaseOrderNumber) {
        String sql ="update purchase_orders set order_status = 'Placed', order_placed_date = current_timestamp() where order_number = ?";
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(purchaseOrderNumber);
        try {
            commonDatabaseOperation.executeUpdate(sql,placeholderValues);
        } catch (SQLException e) {
            throw new ServiceException(e);

        }

    }
    @Override
    public void moveToReceivedOrder(String purchaseOrderNumber) {
        String sql ="update purchase_orders set order_status = 'Received', order_received_date = current_timestamp() where order_number = ?";
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(purchaseOrderNumber);
        try {
            commonDatabaseOperation.executeUpdate(sql,placeholderValues);
        } catch (SQLException e) {
            throw new ServiceException(e);

        }
    }

    @Override
    public void delete(PurchaseOrder purchaseOrder) {
        String sql = "delete from purchase_orders where order_number = ?";
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(purchaseOrder.getOrderNumber());
        try {
            commonDatabaseOperation.executeUpdate(sql,placeholderValues);
        } catch (SQLException e) {
            throw new ServiceException(e);

        }
    }
}
