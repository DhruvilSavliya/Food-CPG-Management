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
    public List<IRawMaterial> getAll(int manufacturerId) {
        List<IRawMaterial> rawMaterials = new ArrayList<>();

        String sql = RawMaterialDatabaseQuery.SELECT_ALL_RAWMATERIALS;
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(manufacturerId);

        try (Connection connection = commonDatabaseOperation.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                commonDatabaseOperation.loadPlaceholderValues(preparedStatement, placeholderValues);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        IRawMaterial rawMaterial = RawMaterialFactory.instance().makeRawMaterial();
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
        String sql = RawMaterialDatabaseQuery.LOAD_RAWMATERIALS;
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
    public Integer save(RawMaterial rawMaterial) {
        Integer rawMaterialId = null;
        String sql = RawMaterialDatabaseQuery.INSERT_RAWMATERIALS;
        List<Object> placeholderValues = new ArrayList<>();
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
        return rawMaterialId;
    }

    @Override
    public void update(RawMaterial rawMaterial) {
        String sql = RawMaterialDatabaseQuery.UPDATE_RAWMATERIALS;
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
        String sql = RawMaterialDatabaseQuery.DELETE_RAWMATERIALS;
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(rawMaterialId);

        try {
            commonDatabaseOperation.executeUpdate(sql, placeholderValues);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    private void loadRawMaterialDetailsFromResultSet(ResultSet resultSet, IRawMaterial rawMaterial) throws SQLException {
        rawMaterial.setId(resultSet.getInt(RawMaterialDatabaseColumn.RAW_MATERIAL_ID));
        rawMaterial.setName(resultSet.getString(RawMaterialDatabaseColumn.RAW_MATERIAL_NAME));
        rawMaterial.setVendorId(resultSet.getInt(RawMaterialDatabaseColumn.VENDO_ID));
        rawMaterial.setUnitCost(resultSet.getDouble(RawMaterialDatabaseColumn.UNIT_COST));
        rawMaterial.setUnitMeasurement(resultSet.getDouble(RawMaterialDatabaseColumn.UNIT_MEASUREMENT));
        rawMaterial.setUnitMeasurementUOM(resultSet.getString(RawMaterialDatabaseColumn.UNIT_MEASUREMENT_UOM));
        rawMaterial.setReorderPointQuantity(resultSet.getDouble(RawMaterialDatabaseColumn.RECORDER_POINT_QUANTITY));
        rawMaterial.setReorderPointQuantityUOM(resultSet.getString(RawMaterialDatabaseColumn.RECORDER_POINT_QUANTITY_UOM));
    }
}