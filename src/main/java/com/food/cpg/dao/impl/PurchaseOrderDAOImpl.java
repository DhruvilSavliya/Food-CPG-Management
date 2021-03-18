package com.food.cpg.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

import com.food.cpg.dao.IPurchaseOrderDAO;
import com.food.cpg.exceptions.DBException;
import com.food.cpg.exceptions.ServiceException;
import com.food.cpg.models.PurchaseOrder;

@Repository
public class PurchaseOrderDAOImpl extends AbstractBaseDAO implements IPurchaseOrderDAO {

    private static final Logger LOG = LoggerFactory.getLogger(PurchaseOrderDAOImpl.class);

    @Autowired
    public PurchaseOrderDAOImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void save(PurchaseOrder purchaseOrder) {
        LOG.info("IN PurchaseOrderDAOImpl : save");

        String insertQuery = "insert into purchase_orders (order_number, vendor_id, total_cost, manufacturer_id) " +
                "values (?, ?, ?, ?)";
        try (Connection connection = getDBConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
                statement.setString(1, purchaseOrder.getOrderNumber());
                statement.setInt(2, purchaseOrder.getVendorId());
                statement.setDouble(3, purchaseOrder.getTotalCost());
                statement.setInt(4, 1);

                statement.executeUpdate();
                LOG.info("Purchase order saved successfully.");
            }
        } catch (DBException | SQLException e) {
            throw new ServiceException(e);
        }
        LOG.info("OUT PurchaseOrderDAOImpl : save");
    }
}
