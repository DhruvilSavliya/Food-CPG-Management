package com.food.cpg.inventory;

import com.food.cpg.databasepersistence.ICommonDatabaseOperation;
import com.food.cpg.exceptions.ServiceException;
import com.food.cpg.item.Item;
import com.food.cpg.vendor.Vendor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RawMaterialInventoryDatabasePersistence  implements IRawMaterialInventoryPersistence{

    private final ICommonDatabaseOperation commonDatabaseOperation;

    public RawMaterialInventoryDatabasePersistence(ICommonDatabaseOperation commonDatabaseOperation) {
        this.commonDatabaseOperation = commonDatabaseOperation;
    }

    @Override
    public List<RawMaterialInventory> getDefaulter() {
        List<RawMaterialInventory> rawMaterialDefaulterList = new ArrayList<>();

        String sql = "select rmi.raw_material_id, rmi.quantity, rmi.quantity_uom, rm.manufacturer_id, rm.raw_material_name, v.vendor_name, m.manufacturer_email  from raw_material_inventory rmi join raw_materials rm on rmi.raw_material_id = rm.raw_material_id join vendors v on rm.vendor_id = v.vendor_id join manufacturer m on rm.manufacturer_id= m.manufacturer_id\n" +
                "where rmi.quantity < rm.reorder_point_quantity";

        try (Connection connection = commonDatabaseOperation.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        RawMaterialInventory rawMaterialInventory = new RawMaterialInventory();
                        loadRawMaterialInventoryDefaulterDetailsFromResultSet(rs, rawMaterialInventory);

                        rawMaterialDefaulterList.add(rawMaterialInventory);
                    }
                }
            }
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
        return rawMaterialDefaulterList;
    }

    @Override
    public List<RawMaterialInventory> getAll(int manufacturerId) {

        List<RawMaterialInventory> rawMaterialInventoryList = new ArrayList<>();

        String sql = "select rmi.raw_material_id, rmi.quantity, rmi. quantity_uom, rm.raw_material_name from raw_material_inventory rmi join raw_materials rm on rmi.raw_material_id = rm.raw_material_id where rm.manufacturer_id = ?";
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(manufacturerId);

        try (Connection connection = commonDatabaseOperation.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                commonDatabaseOperation.loadPlaceholderValues(preparedStatement, placeholderValues);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        RawMaterialInventory rawMaterialInventory = new RawMaterialInventory();
                        loadRawMaterialInventoryDetailsFromResultSet(rs, rawMaterialInventory);
                        rawMaterialInventoryList.add(rawMaterialInventory);
                    }
                }
            }
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
        return rawMaterialInventoryList;
    }

    @Override
    public void save(RawMaterialInventory rawMaterialInventory) {
        String sql = "update raw_material_inventory set quantity = quantity + ?, quantity_uom = ? where raw_material_id = ?";
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(rawMaterialInventory.getRawMaterialQuantity());
        placeholderValues.add(rawMaterialInventory.getRawMaterialQuantityUOM());
        placeholderValues.add(rawMaterialInventory.getRawMaterialId());

        try {
            commonDatabaseOperation.executeUpdate(sql, placeholderValues);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    private void loadRawMaterialInventoryDefaulterDetailsFromResultSet(ResultSet resultSet, RawMaterialInventory rawMaterialInventory) throws SQLException {
        rawMaterialInventory.setRawMaterialId(resultSet.getInt("raw_material_id"));
        rawMaterialInventory.setRawMaterialQuantity(resultSet.getDouble("quantity"));
        rawMaterialInventory.setRawMaterialQuantityUOM(resultSet.getString("quantity_uom"));
        rawMaterialInventory.setManufacturerId(resultSet.getInt("manufacturer_id"));
        rawMaterialInventory.setRawMaterialName(resultSet.getString("raw_material_name"));
        rawMaterialInventory.setVendorName(resultSet.getString("vendor_name"));
        rawMaterialInventory.setManufacturerEmail(resultSet.getString("manufacturer_email"));

    }

    private void loadRawMaterialInventoryDetailsFromResultSet(ResultSet resultSet, RawMaterialInventory rawMaterialInventory) throws SQLException {
        rawMaterialInventory.setRawMaterialId(resultSet.getInt("raw_material_id"));
        rawMaterialInventory.setRawMaterialQuantity(resultSet.getDouble("quantity"));
        rawMaterialInventory.setRawMaterialQuantityUOM(resultSet.getString("quantity_uom"));
        rawMaterialInventory.setRawMaterialName(resultSet.getString("raw_material_name"));

    }
}
