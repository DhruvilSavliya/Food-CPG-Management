package com.food.cpg.purchaseorder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.food.cpg.databasepersistence.ICommonDatabaseOperation;
import com.food.cpg.exceptions.ServiceException;

public class PurchaseOrderRawMaterialDatabasePersistence implements IPurchaseOrderRawMaterialPersistence {

    private final ICommonDatabaseOperation commonDatabaseOperation;

    public PurchaseOrderRawMaterialDatabasePersistence(ICommonDatabaseOperation commonDatabaseOperation) {
        this.commonDatabaseOperation = commonDatabaseOperation;
    }

    @Override
    public void save(PurchaseOrderRawMaterial purchaseOrderRawMaterial) {

        String sql = "insert into purchase_order_raw_materials (purchase_order_number, raw_material_id, quantity, quantity_uom) values (?, ?, ?, ?)";

        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(purchaseOrderRawMaterial.getPurchaseOrderNumber());
        placeholderValues.add(purchaseOrderRawMaterial.getRawMaterialId());
        placeholderValues.add(purchaseOrderRawMaterial.getRawMaterialQuantity());
        placeholderValues.add(purchaseOrderRawMaterial.getRawMaterialQuantityUOM());

        try {
            commonDatabaseOperation.executeUpdate(sql, placeholderValues);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<PurchaseOrderRawMaterial> getPurchaseOrderItemRawMaterial(int itemId) {
        List<PurchaseOrderRawMaterial> purchaseOrderItemRawMaterials = new ArrayList<>();

        String sql = "select * from item_raw_materials where item_id = ?";
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(itemId);
        try (Connection connection = commonDatabaseOperation.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                commonDatabaseOperation.loadPlaceholderValues(preparedStatement, placeholderValues);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        PurchaseOrderRawMaterial purchaseOrderRawMaterial = new PurchaseOrderRawMaterial();
                        purchaseOrderRawMaterial.setRawMaterialId(rs.getInt("raw_material_id"));
                        purchaseOrderRawMaterial.setRawMaterialQuantity(rs.getDouble("raw_material_quantity"));
                        purchaseOrderRawMaterial.setRawMaterialQuantityUOM(rs.getString("raw_material_quantity_uom"));
                        purchaseOrderItemRawMaterials.add(purchaseOrderRawMaterial);
                    }
                }
            }
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
        return purchaseOrderItemRawMaterials;
    }

    @Override
    public void delete(String purchaseOrderNumber) {

        String sql = "delete from purchase_order_raw_materials where purchase_order_number = ?";

        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(purchaseOrderNumber);
        try {
            commonDatabaseOperation.executeUpdate(sql, placeholderValues);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }
}
