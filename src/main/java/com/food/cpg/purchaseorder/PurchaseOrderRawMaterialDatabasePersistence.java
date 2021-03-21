package com.food.cpg.purchaseorder;

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
}
