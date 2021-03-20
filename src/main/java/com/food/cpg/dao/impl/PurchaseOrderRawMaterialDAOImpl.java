package com.food.cpg.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;

import com.food.cpg.dao.IPurchaseOrderRawMaterialDAO;
import com.food.cpg.exceptions.DBException;
import com.food.cpg.exceptions.ServiceException;
import com.food.cpg.models.PurchaseOrderRawMaterial;

@Repository
public class PurchaseOrderRawMaterialDAOImpl extends AbstractBaseDAO implements IPurchaseOrderRawMaterialDAO {

    private static final Logger LOG = LoggerFactory.getLogger(PurchaseOrderRawMaterialDAOImpl.class);

    public static final String PLACEHOLDER_DELIMITER = ",";

    @Autowired
    public PurchaseOrderRawMaterialDAOImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void save(List<PurchaseOrderRawMaterial> purchaseOrderRawMaterials) {
        LOG.info("IN PurchaseOrderRawMaterialDAOImpl : save");

        if (CollectionUtils.isEmpty(purchaseOrderRawMaterials)) {
            return;
        }

        List<Object> placeholderValues = new ArrayList<>();
        StringJoiner placeholderJoiner = new StringJoiner(PLACEHOLDER_DELIMITER);
        for (PurchaseOrderRawMaterial purchaseOrderRawMaterial : purchaseOrderRawMaterials) {
            placeholderValues.add(purchaseOrderRawMaterial.getPurchaseOrderNumber());
            placeholderValues.add(purchaseOrderRawMaterial.getRawMaterialId());
            placeholderValues.add(purchaseOrderRawMaterial.getRawMaterialQuantity());
            placeholderValues.add(purchaseOrderRawMaterial.getRawMaterialQuantityUOM());
            placeholderJoiner.add("(?, ?, ?, ?)");
        }

        String placeholders = placeholderJoiner.toString();

        String insertQuery = "insert into purchase_order_raw_materials (purchase_order_number, raw_material_id, quantity, quantity_uom) values " + placeholders;
        try (Connection connection = getDBConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
                int index = 0;
                for (Object placeHolderValue : placeholderValues) {
                    statement.setObject(++index, placeHolderValue);
                }

                statement.executeUpdate();
                LOG.info("Purchase order raw materials saved successfully.");
            }
        } catch (DBException | SQLException e) {
            throw new ServiceException(e);
        }
        LOG.info("OUT PurchaseOrderRawMaterialDAOImpl : save");
    }
}
