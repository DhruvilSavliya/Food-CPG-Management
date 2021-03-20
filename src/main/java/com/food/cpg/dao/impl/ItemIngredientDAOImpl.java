package com.food.cpg.dao.impl;

import com.food.cpg.constants.StringConstants;
import com.food.cpg.dao.IItemIngredientDAO;
import com.food.cpg.exceptions.DBException;
import com.food.cpg.exceptions.ServiceException;
import com.food.cpg.models.ItemIngredient;
import com.food.cpg.models.PurchaseOrderRawMaterial;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

@Repository
public class ItemIngredientDAOImpl extends AbstractBaseDAO implements IItemIngredientDAO {

    private static final Logger LOG = LoggerFactory.getLogger(ItemIngredientDAOImpl.class);

    @Autowired
    public ItemIngredientDAOImpl(DataSource dataSource) {
        super(dataSource);
    }


    @Override
    public void saveItemIngredient(List<ItemIngredient> itemIngredients, Integer itemId) {
        LOG.info("IN ItemIngredientDAOImpl : save");

        if (CollectionUtils.isEmpty(itemIngredients)) {
            return;
        }

        List<Object> placeholderValues = new ArrayList<>();
        StringJoiner placeholderJoiner = new StringJoiner(StringConstants.COMMA);
        for (ItemIngredient itemIngredient : itemIngredients) {
            placeholderValues.add(itemId);
            placeholderValues.add(itemIngredient.getRawMaterialId());
            placeholderValues.add(itemIngredient.getRawMaterialQuantity());
            placeholderValues.add(itemIngredient.getRawMaterialQuantityUOM());
            placeholderJoiner.add("(?, ?, ?, ?)");
        }

        String placeholders = placeholderJoiner.toString();

        String insertQuery = "insert into item_ingredients (item_id, raw_material_id, raw_material_quantity, raw_material_quantity_uom) values " + placeholders;
        try (Connection connection = getDBConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
                int index = 0;
                for (Object placeHolderValue : placeholderValues) {
                    statement.setObject(++index, placeHolderValue);
                }

                statement.executeUpdate();
                LOG.info("Item ingredients saved successfully.");
            }
        } catch (DBException | SQLException e) {
            throw new ServiceException(e);
        }
        LOG.info("OUT ItemIngredientDAOImpl : save");
    }
}
