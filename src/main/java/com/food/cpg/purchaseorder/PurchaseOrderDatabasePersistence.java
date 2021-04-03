package com.food.cpg.purchaseorder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    public List<PurchaseOrder> getOpenPurchaseOrder(int manufacturerId) {
        return getPurchaseOrders(manufacturerId, PurchaseOrderStatus.Status.OPEN.name());
    }

    @Override
    public List<PurchaseOrder> getPlacedPurchaseOrder(int manufacturerId) {
        return getPurchaseOrders(manufacturerId, PurchaseOrderStatus.Status.PLACED.name());
    }

    @Override
    public List<PurchaseOrder> getReceivedPurchaseOrder(int manufacturerId) {
        return getPurchaseOrders(manufacturerId, PurchaseOrderStatus.Status.RECEIVED.name());
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
    public void changeStatus(String orderNumber, String orderStatus) {
        String sql = "update purchase_orders set order_status = ?, status_change_date = current_timestamp() where order_number = ?";
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(orderStatus);
        placeholderValues.add(orderNumber);
        try {
            commonDatabaseOperation.executeUpdate(sql, placeholderValues);
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
            commonDatabaseOperation.executeUpdate(sql, placeholderValues);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void load(PurchaseOrder purchaseOrder) {
        String sql = "select * from purchase_orders where order_number = ?";
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(purchaseOrder.getOrderNumber());

        try (Connection connection = commonDatabaseOperation.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                commonDatabaseOperation.loadPlaceholderValues(preparedStatement, placeholderValues);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    if (rs.next()) {
                        loadPurchaseOrderDetailsFromResultSet(rs, purchaseOrder);
                    }
                }
            }
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    private List<PurchaseOrder> getPurchaseOrders(int manufacturerId, String orderStatus) {
        List<PurchaseOrder> purchaseOrders = new ArrayList<>();

        String sql = "select * from purchase_orders where manufacturer_id = ? and order_status = ?";
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(manufacturerId);
        placeholderValues.add(orderStatus);

        try (Connection connection = commonDatabaseOperation.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                commonDatabaseOperation.loadPlaceholderValues(preparedStatement, placeholderValues);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        PurchaseOrder purchaseOrder = new PurchaseOrder();
                        loadPurchaseOrderDetailsFromResultSet(rs, purchaseOrder);

                        purchaseOrders.add(purchaseOrder);
                    }
                }
            }
        } catch (SQLException e) {
            throw new ServiceException(e);
        }

        return purchaseOrders;
    }

    private void loadPurchaseOrderDetailsFromResultSet(ResultSet resultSet, PurchaseOrder purchaseOrder) throws SQLException {
        purchaseOrder.setOrderNumber(resultSet.getString("order_number"));
        purchaseOrder.setTotalCost(resultSet.getDouble("total_cost"));
        purchaseOrder.setStatusChangeDate(resultSet.getTimestamp("status_change_date"));
        String orderStatus = resultSet.getString("order_status");
        PurchaseOrderStatus purchaseOrderStatus = PurchaseOrderStatusFactory.getInstance().makeOrderStatus(orderStatus);
        purchaseOrder.setPurchaseOrderStatus(purchaseOrderStatus);

    }
}
