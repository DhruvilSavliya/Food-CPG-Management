package com.food.cpg.salesorder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.food.cpg.databasepersistence.ICommonDatabaseOperation;
import com.food.cpg.exceptions.ServiceException;

public class SalesOrderDatabasePersistence implements ISalesOrderPersistence {

    private final ICommonDatabaseOperation commonDatabaseOperation;

    public SalesOrderDatabasePersistence(ICommonDatabaseOperation commonDatabaseOperation) {
        this.commonDatabaseOperation = commonDatabaseOperation;
    }


    @Override
    public List<SalesOrder> getAllOpenOrders(int manufacturerId) {
        return getAllOrders(manufacturerId, SalesOrderStatus.Status.OPEN.name());
    }

    @Override
    public List<SalesOrder> getAllPackagedOrders(int manufacturerId) {
        return getAllOrders(manufacturerId, SalesOrderStatus.Status.PACKAGED.name());
    }

    @Override
    public List<SalesOrder> getAllShippedOrders(int manufacturerId) {
        return getAllOrders(manufacturerId, SalesOrderStatus.Status.SHIPPED.name());
    }

    @Override
    public List<SalesOrder> getAllPaidOrders(int manufacturerId) {
        return getAllOrders(manufacturerId, SalesOrderStatus.Status.PAID.name());
    }

    @Override
    public void load(SalesOrder salesOrder) {
        String sql = "select * from sales_orders where order_number = ?";
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(salesOrder.getOrderNumber());

        try (Connection connection = commonDatabaseOperation.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                commonDatabaseOperation.loadPlaceholderValues(preparedStatement, placeholderValues);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    if (rs.next()) {
                        loadSalesOrderDetailsFromResultSet(rs, salesOrder);
                    }
                }
            }
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void delete(String orderNumber) {
        String sql = "delete from sales_orders where order_number = ?";
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(orderNumber);

        try {
            commonDatabaseOperation.executeUpdate(sql, placeholderValues);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void changeStatus(String orderNumber, String orderStatus) {
        String sql = "update sales_orders set order_status = ? where order_number = ?";
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(orderStatus);
        placeholderValues.add(orderNumber);

        try {
            commonDatabaseOperation.executeUpdate(sql, placeholderValues);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    private List<SalesOrder> getAllOrders(int manufacturerId, String orderStatus) {
        List<SalesOrder> salesOrders = new ArrayList<>();

        String sql = "select * from sales_orders where manufacturer_id = ? and order_status = ?";
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(manufacturerId);
        placeholderValues.add(orderStatus);

        try (Connection connection = commonDatabaseOperation.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                commonDatabaseOperation.loadPlaceholderValues(preparedStatement, placeholderValues);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        SalesOrder salesOrder = new SalesOrder();
                        loadSalesOrderDetailsFromResultSet(rs, salesOrder);

                        salesOrders.add(salesOrder);
                    }
                }
            }
        } catch (SQLException e) {
            throw new ServiceException(e);
        }

        return salesOrders;
    }

    private void loadSalesOrderDetailsFromResultSet(ResultSet resultSet, SalesOrder salesOrder) throws SQLException {
        salesOrder.setOrderNumber(resultSet.getString("order_number"));
        salesOrder.setItemId(resultSet.getInt("item_id"));
        salesOrder.setPackageId(resultSet.getInt("package_id"));
        salesOrder.setTotalCost(resultSet.getDouble("total_cost"));
        salesOrder.setForCharity(resultSet.getBoolean("is_for_charity"));

        String orderStatus = resultSet.getString("order_status");
        SalesOrderStatus salesOrderStatus = SalesOrderStatusFactory.getInstance().makeOrderStatus(orderStatus);
        salesOrder.setSalesOrderStatus(salesOrderStatus);

        salesOrder.setStatusChangeDate(resultSet.getTimestamp("status_change_date"));
    }
}