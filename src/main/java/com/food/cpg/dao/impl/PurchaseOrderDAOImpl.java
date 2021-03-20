package com.food.cpg.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
    @Override
    public List<PurchaseOrder> getPurchaseOrder(int manufacturerId) {
        LOG.info("IN PurchaseOrderDAOImpl : getPurchaseOrder : manufacturerId - {}", manufacturerId);
        List<PurchaseOrder> purchaseOrders = new ArrayList<>();
        try (Connection connection = getDBConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("select * from purchase_orders where manufacturer_id = ? and order_status = 'Open'" )) {
                statement.setInt(1, manufacturerId);
                try (ResultSet rs = statement.executeQuery()) {
                    if (rs != null) {
                        while (rs.next()) {
                            PurchaseOrder purchaseOrder = new PurchaseOrder();
                            purchaseOrder.setOrderNumber(rs.getString("order_number"));
                            purchaseOrder.setTotalCost(rs.getDouble("total_cost"));
                            purchaseOrders.add(purchaseOrder);
                        }
                    }
                }
            }
        } catch (DBException | SQLException e) {
            throw new ServiceException(e);
        }

        LOG.info("OUT PurchaseOrderDAOImpl : getPurchaseOrder : Purchase Order count - {}", purchaseOrders.size());
        return purchaseOrders;
    }
    @Override
    public List<PurchaseOrder> getPlacedOrder(int manufacturerId) {
        LOG.info("IN PurchaseOrderDAOImpl : getPlacedOrder : manufacturerId - {}", manufacturerId);
        List<PurchaseOrder> placedOrders = new ArrayList<>();
        try (Connection connection = getDBConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("select * from purchase_orders where manufacturer_id = ? and order_status = 'Placed'" )) {
                statement.setInt(1, manufacturerId);
                try (ResultSet rs = statement.executeQuery()) {
                    if (rs != null) {
                        while (rs.next()) {
                            PurchaseOrder purchaseOrder = new PurchaseOrder();
                            purchaseOrder.setOrderNumber(rs.getString("order_number"));
                            purchaseOrder.setTotalCost(rs.getDouble("total_cost"));
                            purchaseOrder.setOrderPlacedDate(rs.getTimestamp("order_placed_date"));
                            placedOrders.add(purchaseOrder);
                        }
                    }
                }
            }
        } catch (DBException | SQLException e) {
            throw new ServiceException(e);
        }

        LOG.info("OUT PurchaseOrderDAOImpl : getPlacedOrder : Purchase Order count - {}", placedOrders.size());
        return placedOrders;
    }
    @Override
    public List<PurchaseOrder> getReceivedOrder(int manufacturerId) {
        LOG.info("IN PurchaseOrderDAOImpl : getReceivedOrder : manufacturerId - {}", manufacturerId);
        List<PurchaseOrder> receivedOrders = new ArrayList<>();
        try (Connection connection = getDBConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("select * from purchase_orders where manufacturer_id = ? and order_status = 'Received'" )) {
                statement.setInt(1, manufacturerId);
                try (ResultSet rs = statement.executeQuery()) {
                    if (rs != null) {
                        while (rs.next()) {
                            PurchaseOrder purchaseOrder = new PurchaseOrder();
                            purchaseOrder.setOrderNumber(rs.getString("order_number"));
                            purchaseOrder.setTotalCost(rs.getDouble("total_cost"));
                            purchaseOrder.setOrderReceivedDate(rs.getTimestamp("order_received_date"));
                            receivedOrders.add(purchaseOrder);
                        }
                    }
                }
            }
        } catch (DBException | SQLException e) {
            throw new ServiceException(e);
        }

        LOG.info("OUT PurchaseOrderDAOImpl : getReceivedOrder : Purchase Order count - {}", receivedOrders.size());
        return receivedOrders;
    }

    @Override
    public void moveToPlacedOrder(String orderNumber) {
        LOG.info("IN PurchaseOrderDAOImpl : moveToPlacedOrder : orderNumber - {}", orderNumber);
        try (Connection connection = getDBConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("update purchase_orders set order_status = 'Placed', order_placed_date = current_timestamp() where order_number = ?" )) {
                statement.setString(1, orderNumber);
                statement.executeUpdate();
            }
        } catch (DBException | SQLException e) {
            throw new ServiceException(e);
        }
        LOG.info("OUT PurchaseOrderDAOImpl : moveToPlacedOrder");
    }
    @Override
    public void moveToReceivedOrder(String orderNumber) {
        LOG.info("IN PurchaseOrderDAOImpl : moveToReceivedOrder : orderNumber - {}", orderNumber);
        try (Connection connection = getDBConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("update purchase_orders set order_status = 'Received', order_received_date = current_timestamp() where order_number = ?" )) {
                statement.setString(1, orderNumber);
                statement.executeUpdate();
            }
        } catch (DBException | SQLException e) {
            throw new ServiceException(e);
        }
        LOG.info("OUT PurchaseOrderDAOImpl : moveToReceivedOrder");
    }

    @Override
    public void delete(String orderNumber){
        LOG.info("IN PurchaseOrderDAOImpl : delete");
        try (Connection connection = getDBConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("delete from purchase_orders where order_number = ?" )) {
                statement.setString(1, orderNumber);
                statement.executeUpdate();
            }
        } catch (DBException | SQLException e) {
            throw new ServiceException(e);
        }
        LOG.info("OUT PurchaseOrderDAOImpl : delete");

    }



}