package com.food.cpg.rawmaterial;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.food.cpg.databasepersistence.ICommonDatabaseOperation;
import com.food.cpg.exceptions.ServiceException;

public class RawMaterialDatabasePersistence implements IRawMaterialPersistence {

    private final ICommonDatabaseOperation commonDatabaseOperation;

    public RawMaterialDatabasePersistence(ICommonDatabaseOperation commonDatabaseOperation) {
        this.commonDatabaseOperation = commonDatabaseOperation;
    }

    @Override
    public List<RawMaterial> getAll(int manufacturerId) {
        List<RawMaterial> rawMaterials = new ArrayList<>();

        String sql = "select * from raw_materials where manufacturer_id = ?";
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(manufacturerId);

        try (Connection connection = commonDatabaseOperation.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                commonDatabaseOperation.loadPlaceholderValues(preparedStatement, placeholderValues);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        RawMaterial rawMaterial = new RawMaterial();
                        loadRawMaterialDetailsFromResultSet(rs, rawMaterial);

                        rawMaterials.add(rawMaterial);
                    }
                }
            }
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
        return rawMaterials;
    }

    @Override
    public void load(RawMaterial rawMaterial) {
        String sql = "select * from raw_materials where raw_material_id = ?";
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(rawMaterial.getId());

        try (Connection connection = commonDatabaseOperation.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                commonDatabaseOperation.loadPlaceholderValues(preparedStatement, placeholderValues);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    if (rs.next()) {
                        loadRawMaterialDetailsFromResultSet(rs, rawMaterial);
                    }
                }
            }
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void save(RawMaterial rawMaterial) {
        Integer rawMaterialId = null;
        String sql = "insert into raw_materials (raw_material_name, vendor_id, unit_cost, unit_measurement, unit_measurement_uom, reorder_point_quantity, reorder_point_quantity_uom, manufacturer_id) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?)";
        String inventorySql = "insert into raw_material_inventory (raw_material_id) values(?)";
        List<Object> placeholderValues = new ArrayList<>();
        List<Object> placeholderValuesInventory = new ArrayList<>();
        placeholderValues.add(rawMaterial.getName());
        placeholderValues.add(rawMaterial.getVendorId());
        placeholderValues.add(rawMaterial.getUnitCost());
        placeholderValues.add(rawMaterial.getUnitMeasurement());
        placeholderValues.add(rawMaterial.getUnitMeasurementUOM());
        placeholderValues.add(rawMaterial.getReorderPointQuantity());
        placeholderValues.add(rawMaterial.getReorderPointQuantityUOM());
        placeholderValues.add(rawMaterial.getManufacturerId());

        try {
            rawMaterialId = commonDatabaseOperation.executeUpdateGetId(sql, placeholderValues);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }

        placeholderValuesInventory.add(rawMaterialId);
        try {
            commonDatabaseOperation.executeUpdate(inventorySql, placeholderValuesInventory);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void update(RawMaterial rawMaterial) {
        String sql = "update raw_materials set raw_material_name = ?, vendor_id = ?, unit_cost = ?, unit_measurement = ?, unit_measurement_uom = ?, reorder_point_quantity = ?, reorder_point_quantity_uom = ? where raw_material_id = ?";
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(rawMaterial.getName());
        placeholderValues.add(rawMaterial.getVendorId());
        placeholderValues.add(rawMaterial.getUnitCost());
        placeholderValues.add(rawMaterial.getUnitMeasurement());
        placeholderValues.add(rawMaterial.getUnitMeasurementUOM());
        placeholderValues.add(rawMaterial.getReorderPointQuantity());
        placeholderValues.add(rawMaterial.getReorderPointQuantityUOM());
        placeholderValues.add(rawMaterial.getId());

        try {
            commonDatabaseOperation.executeUpdate(sql, placeholderValues);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void delete(int rawMaterialId) {
        String sql = "delete from raw_materials where raw_material_id = ?";
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(rawMaterialId);

        try {
            commonDatabaseOperation.executeUpdate(sql, placeholderValues);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    private void loadRawMaterialDetailsFromResultSet(ResultSet resultSet, RawMaterial rawMaterial) throws SQLException {
        rawMaterial.setId(resultSet.getInt("raw_material_id"));
        rawMaterial.setName(resultSet.getString("raw_material_name"));
        rawMaterial.setVendorId(resultSet.getInt("vendor_id"));
        rawMaterial.setUnitCost(resultSet.getDouble("unit_cost"));
        rawMaterial.setUnitMeasurement(resultSet.getDouble("unit_measurement"));
        rawMaterial.setUnitMeasurementUOM(resultSet.getString("unit_measurement_uom"));
        rawMaterial.setReorderPointQuantity(resultSet.getDouble("reorder_point_quantity"));
        rawMaterial.setReorderPointQuantityUOM(resultSet.getString("reorder_point_quantity_uom"));
    }
}