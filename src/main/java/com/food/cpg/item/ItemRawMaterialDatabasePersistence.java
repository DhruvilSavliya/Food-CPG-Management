package com.food.cpg.item;

import com.food.cpg.databasepersistence.ICommonDatabaseOperation;
import com.food.cpg.exceptions.ServiceException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemRawMaterialDatabasePersistence implements IItemRawMaterialPersistence{

    private final ICommonDatabaseOperation commonDatabaseOperation;

    public ItemRawMaterialDatabasePersistence(ICommonDatabaseOperation commonDatabaseOperation) {
        this.commonDatabaseOperation = commonDatabaseOperation;
    }

    @Override
    public void save(ItemRawMaterial itemRawMaterial) {

        String sql = "insert into item_raw_materials (item_id, raw_material_id, vendor_id, raw_material_quantity, raw_material_quantity_uom, raw_material_unit_cost, cost) values (?, ?, ?, ?,?,?,?)";

        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(itemRawMaterial.getItemId());
        placeholderValues.add(itemRawMaterial.getRawMaterialId());
        placeholderValues.add(itemRawMaterial.getVendorId());
        placeholderValues.add(itemRawMaterial.getRawMaterialQuantity());
        placeholderValues.add(itemRawMaterial.getRawMaterialQuantityUOM());
        placeholderValues.add(itemRawMaterial.getRawMaterialUnitCost());
        placeholderValues.add(itemRawMaterial.getCost());

        try {
            commonDatabaseOperation.executeUpdate(sql, placeholderValues);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public void delete(int itemId) {

        String sql = "delete from item_raw_materials where item_id = ?";
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(itemId);

        try {
            commonDatabaseOperation.executeUpdate(sql, placeholderValues);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Double loadUnitCost(Integer rawMaterialId) {

        Double unitCost = 0.0;
        String sql = "select unit_cost from raw_materials where raw_material_id = ?";
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(rawMaterialId);

        try (Connection connection = commonDatabaseOperation.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                commonDatabaseOperation.loadPlaceholderValues(preparedStatement, placeholderValues);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        unitCost = resultSet.getDouble("unit_cost");

                    }
                }
            }
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
        return unitCost;
    }
}
