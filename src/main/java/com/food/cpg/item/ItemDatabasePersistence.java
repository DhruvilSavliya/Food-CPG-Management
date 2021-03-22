package com.food.cpg.item;

import com.food.cpg.databasepersistence.ICommonDatabaseOperation;
import com.food.cpg.exceptions.ServiceException;
import com.food.cpg.vendor.Vendor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDatabasePersistence implements IItemPersistence{

    private final ICommonDatabaseOperation commonDatabaseOperation;

    public ItemDatabasePersistence(ICommonDatabaseOperation commonDatabaseOperation) {
        this.commonDatabaseOperation = commonDatabaseOperation;
    }

    @Override
    public List<Item> getAll(int manufacturerId) {

        List<Item> itemList = new ArrayList<>();

        String sql = "select * from items where manufacturer_id = ?";
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(manufacturerId);

        try (Connection connection = commonDatabaseOperation.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                commonDatabaseOperation.loadPlaceholderValues(preparedStatement, placeholderValues);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        Item item = new Item();
                        loadItemDetailsFromResultSet(rs, item);
                        itemList.add(item);
                    }
                }
            }
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
        return itemList;
    }

    @Override
    public void load(Item item) {

        String sql = "select * from items where item_id = ?";
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(item.getId());

        try (Connection connection = commonDatabaseOperation.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                commonDatabaseOperation.loadPlaceholderValues(preparedStatement, placeholderValues);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    if (rs.next()) {
                        loadItemDetailsFromResultSet(rs, item);
                    }
                }
            }
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Integer save(Item item) {
        Integer itemId = null;
        String sql = "insert into items (item_name, item_cooking_cost, item_total_cost, manufacturer_id) values (?, ?, ?, ?)";
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(item.getName());
        placeholderValues.add(item.getCookingCost());
        placeholderValues.add(item.getTotalCost());
        placeholderValues.add(item.getManufacturerId());

        try {
           itemId = commonDatabaseOperation.executeUpdateGetId(sql, placeholderValues);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
        return itemId;
    }

    @Override
    public void delete(int itemId) {

        String sql = "delete from items where item_id = ?";
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(itemId);

        try {
            commonDatabaseOperation.executeUpdate(sql, placeholderValues);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }

    }

    private void loadItemDetailsFromResultSet(ResultSet resultSet, Item item) throws SQLException {
        item.setId(resultSet.getInt("item_id"));
        item.setName(resultSet.getString("item_name"));
        item.setCookingCost(resultSet.getDouble("item_cooking_cost"));
        item.setTotalCost(resultSet.getDouble("item_total_cost"));
    }
}
