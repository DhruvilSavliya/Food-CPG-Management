package com.food.cpg.manufacturingorder;

import com.food.cpg.databasepersistence.ICommonDatabaseOperation;
import com.food.cpg.exceptions.ServiceException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManufactureOrderDatabasePersistence implements IManufactureOrderPersistence{

    private final ICommonDatabaseOperation commonDatabaseOperation;

    public ManufactureOrderDatabasePersistence(ICommonDatabaseOperation commonDatabaseOperation) {
        this.commonDatabaseOperation = commonDatabaseOperation;
    }

    @Override
    public List<ManufactureOrder> getAllOpenOrders(int manufacturerId) {
        return getAllOrders(manufacturerId, ManufactureOrderStatus.Status.OPEN.name());
    }

    @Override
    public List<ManufactureOrder> getAllManufacturedOrders(int manufacturerId) {
        return getAllOrders(manufacturerId, ManufactureOrderStatus.Status.MANUFACTURED.name());
    }

    @Override
    public List<ManufactureOrder> getAllPackagedOrders(int manufacturerId) {
        return getAllOrders(manufacturerId, ManufactureOrderStatus.Status.PACKAGED.name());
    }

    @Override
    public List<ManufactureOrder> getAllOrders(int manufacturerId, String orderStatus) {
        List<ManufactureOrder> manufactureOrders = new ArrayList<>();

        String sql = "select * from manufacture_orders where manufacturer_id = ? and order_status = ?";
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(manufacturerId);
        placeholderValues.add(orderStatus);

        try (Connection connection = commonDatabaseOperation.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                commonDatabaseOperation.loadPlaceholderValues(preparedStatement, placeholderValues);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        ManufactureOrder manufactureOrder = new ManufactureOrder();
                        loadManufactureOrderDetailsFromResultSet(rs, manufactureOrder);

                        manufactureOrders.add(manufactureOrder);
                    }
                }
            }
        } catch (SQLException e) {
            throw new ServiceException(e);
        }

        return manufactureOrders;

    }

    @Override
    public void changeStatus(String orderNumber, String orderStatus) {
        String sql = "update manufacture_orders set order_status = ? where order_number = ?";
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
    public void load(ManufactureOrder manufactureOrder) {
        String sql = "select * from manufacture_orders where order_number = ?";
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(manufactureOrder.getOrderNumber());

        try (Connection connection = commonDatabaseOperation.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                commonDatabaseOperation.loadPlaceholderValues(preparedStatement, placeholderValues);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    if (rs.next()) {
                        loadManufactureOrderDetailsFromResultSet(rs, manufactureOrder);
                    }
                }
            }
        } catch (SQLException e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public void save(ManufactureOrder manufactureOrder) {

        String sql = "insert into manufacture_orders (order_number, item_id, quantity, quantity_uom, manufacturing_cost, tax, cost, manufacturer_id) values ( ?, ?, ?, ?, ?, ?, ?, ?)";
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(manufactureOrder.getOrderNumber());
        placeholderValues.add(manufactureOrder.getItemId());
        placeholderValues.add(manufactureOrder.getItemQuantity());
        placeholderValues.add(manufactureOrder.getItemQuantityUOM());
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
    public void delete(String orderNumber) {
        String sql = "delete from manufacture_orders where order_number = ?";
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(orderNumber);

        try {
            commonDatabaseOperation.executeUpdate(sql, placeholderValues);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }

    }

    public Double loadItemCost(int itemId){
        Double itemCost = null;
        String sql = "select item_total_cost from items where item_id = ?";
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(itemId);

        try (Connection connection = commonDatabaseOperation.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                commonDatabaseOperation.loadPlaceholderValues(preparedStatement, placeholderValues);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    if (rs.next()) {
                        itemCost = rs.getDouble("item_total_cost");
                    }
                }
            }
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
        return itemCost;
    }

    private void loadManufactureOrderDetailsFromResultSet(ResultSet resultSet, ManufactureOrder manufactureOrder) throws SQLException {
        manufactureOrder.setOrderNumber(resultSet.getString("order_number"));
        manufactureOrder.setItemId(resultSet.getInt("item_id"));
        manufactureOrder.setItemQuantity(resultSet.getDouble("quantity"));
        manufactureOrder.setItemQuantityUOM(resultSet.getString("quantity_uom"));
        manufactureOrder.setManufacturingCost(resultSet.getDouble("manufacturing_cost"));
        manufactureOrder.setCost(resultSet.getDouble("cost"));

        String orderStatus = resultSet.getString("order_status");
        ManufactureOrderStatus manufactureOrderStatus = ManufactureOrderStatusFactory.getInstance().makeOrderStatus(orderStatus);
        manufactureOrder.setManufactureOrderStatus(manufactureOrderStatus);

        manufactureOrder.setStatusChangeDate(resultSet.getTimestamp("status_change_date"));
    }

}
