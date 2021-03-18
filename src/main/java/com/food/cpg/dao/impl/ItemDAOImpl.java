package com.food.cpg.dao.impl;

import com.food.cpg.dao.IItemDAO;
import com.food.cpg.exceptions.DBException;
import com.food.cpg.exceptions.ServiceException;
import com.food.cpg.models.Item;
import com.food.cpg.models.ItemIngredient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ItemDAOImpl extends AbstractBaseDAO implements IItemDAO {

    private static final Logger LOG = LoggerFactory.getLogger(ItemDAOImpl.class);

    @Autowired
    public ItemDAOImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public List<Item> getItemsList(int manufacturerId) {
        LOG.info("IN ItemDAOImpl : getItemList : manufacturerId - {}", manufacturerId);
        List<Item> itemList = new ArrayList<>();
        try (Connection connection = getDBConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("select * from items where manufacturer_id = ?")) {
                statement.setInt(1, manufacturerId);
                try (ResultSet rs = statement.executeQuery()) {
                    if (rs != null) {
                        while (rs.next()) {
                            Item item = new Item();
                            item.setItemId(rs.getInt("item_id"));
                            item.setItemName(rs.getString("item_name"));
                            item.setItemCookingCost(rs.getDouble("item_cooking_cost"));
                            item.setItemTotalCost(rs.getDouble("item_total_cost"));

                            itemList.add(item);
                        }
                    }
                }
            }
        } catch (DBException | SQLException e) {
            throw new ServiceException(e);
        }

        LOG.info("OUT ItemImpl : getItemList : Item count - {}", itemList.size());
        return itemList;
    }

    @Override
    public Item getItem(int itemId) {
        return null;
    }

    @Override
    public void saveItem(Item item) {
        LOG.info("IN ItemDAOImpl : saveItem");

        String insertQuery = "insert into items (item_name, item_cooking_cost, item_total_cost, manufacturer_id) " +
                "values (?, ?, ?, ?)";
        String insertIngredientQuery = "insert into item_ingredients(raw_material_id, raw_material_quantity, raw_material_quantity_uom) values (?,?,?)";
        try (Connection connection = getDBConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
                statement.setString(1, item.getItemName());
                statement.setDouble(2, item.getItemCookingCost());
                statement.setDouble(3, item.getItemTotalCost());
                statement.setInt(4, 1);

                statement.executeUpdate();
            }
            try (PreparedStatement statement = connection.prepareStatement(insertIngredientQuery)) {
                for(ItemIngredient itemIngredients : item.getItemIngredientsList()) {
                    statement.setInt(1, itemIngredients.getRawMaterialId());
                    statement.setDouble(2, itemIngredients.getRawMaterialQuantity());
                    statement.setString(3, itemIngredients.getRawMaterialMeasurementUOM());
                    statement.executeUpdate();
                }

                LOG.info("Item saved successfully.");
            }
        } catch (DBException | SQLException e) {
            throw new ServiceException(e);
        }
        LOG.info("OUT ItemDAOImpl : saveItem");
    }

    @Override
    public void updateItem(Item item) {

    }

    @Override
    public void deleteItem(int itemId) {

    }
}
