package com.food.cpg.salesorder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.food.cpg.databasepersistence.ICommonDatabaseOperation;
import com.food.cpg.exceptions.ServiceException;
import com.food.cpg.manufacturingorder.ManufactureOrder;

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
    public void save(SalesOrder salesOrder) {

        String sql = "insert into sales_orders (order_number, item_id, package_id, package_cost, shipping_cost, tax, total_cost, is_for_charity, buyer_details, manufacturer_id) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(salesOrder.getOrderNumber());
        placeholderValues.add(salesOrder.getItemId());
        placeholderValues.add(salesOrder.getPackageId());
        placeholderValues.add(salesOrder.getPackageCost());
        placeholderValues.add(salesOrder.getShippingCost());
        placeholderValues.add(salesOrder.getTax());
        placeholderValues.add(salesOrder.getTotalCost());
        placeholderValues.add(salesOrder.getIsForCharity());
        placeholderValues.add(salesOrder.getBuyerDetails());
        placeholderValues.add(salesOrder.getManufacturerId());

        try {
            commonDatabaseOperation.executeUpdate(sql, placeholderValues);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    public Double loadPackageCost(int packageId){
        Double packageManufacturingCost = null;
        String sql = "select manufacturing_cost from packages where package_id = ?";
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(packageId);

        try (Connection connection = commonDatabaseOperation.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                commonDatabaseOperation.loadPlaceholderValues(preparedStatement, placeholderValues);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    if (rs.next()) {
                        packageManufacturingCost = rs.getDouble("manufacturing_cost");
                    }
                }
            }
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
        return packageManufacturingCost;
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
        salesOrder.setIsForCharity(resultSet.getBoolean("is_for_charity"));

        String orderStatus = resultSet.getString("order_status");
        SalesOrderStatus salesOrderStatus = SalesOrderStatusFactory.getInstance().makeOrderStatus(orderStatus);
        salesOrder.setSalesOrderStatus(salesOrderStatus);

        salesOrder.setStatusChangeDate(resultSet.getTimestamp("status_change_date"));
    }
}