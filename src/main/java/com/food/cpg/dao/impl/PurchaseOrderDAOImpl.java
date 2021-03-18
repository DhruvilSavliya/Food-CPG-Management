package com.food.cpg.dao.impl;

import com.food.cpg.dao.IPurchaseOrderDAO;
import com.food.cpg.exceptions.DBException;
import com.food.cpg.exceptions.ServiceException;
import com.food.cpg.models.PurchaseOrder;
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

/**
 * @author P.M
 */


@Repository
public class PurchaseOrderDAOImpl extends AbstractBaseDAO implements IPurchaseOrderDAO {
    private static final Logger LOG = LoggerFactory.getLogger(RawMaterialDAOImpl.class);

    @Autowired
    public PurchaseOrderDAOImpl(DataSource dataSource) {
        super(dataSource);
    }


    @Override
    public List<PurchaseOrder> getPurchaseOrderList(int manufacturerId) {
        LOG.info("IN PurchaseOrderDAOImpl : getPurchaseOrderList : manufacturerId - {}", manufacturerId);
        List<PurchaseOrder> purchaseOrderList = new ArrayList<>();
        try (Connection connection = getDBConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("select * from purchase_orders where manufacturer_id = ? and order_status = 'Open'" )) {
                statement.setInt(1, manufacturerId);
                try (ResultSet rs = statement.executeQuery()) {
                    if (rs != null) {
                        while (rs.next()) {
                            PurchaseOrder purchaseOrder = new PurchaseOrder();
                            purchaseOrder.setOrderid(rs.getString("order_number"));
                            purchaseOrder.setCost(rs.getDouble("total_cost"));
                            purchaseOrderList.add(purchaseOrder);
                        }
                    }
                }
            }
        } catch (DBException | SQLException e) {
            throw new ServiceException(e);
        }

        LOG.info("OUT PurchaseOrderDAOImpl : getPurchaseOrderList : Purchase Order count - {}", purchaseOrderList.size());
        return purchaseOrderList;
    }
    @Override
    public List<PurchaseOrder> getPlacedOrderList(int manufacturerId) {
        LOG.info("IN PurchaseOrderDAOImpl : getPurchaseOrderList : manufacturerId - {}", manufacturerId);
        List<PurchaseOrder> placedOrderList = new ArrayList<>();
        try (Connection connection = getDBConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("select * from purchase_orders where manufacturer_id = ? and order_status = 'Placed'" )) {
                statement.setInt(1, manufacturerId);
                try (ResultSet rs = statement.executeQuery()) {
                    if (rs != null) {
                        while (rs.next()) {
                            PurchaseOrder purchaseOrder = new PurchaseOrder();
                            purchaseOrder.setOrderid(rs.getString("order_number"));
                            purchaseOrder.setCost(rs.getDouble("total_cost"));
                            purchaseOrder.setDate(rs.getString("order_placed_date"));
                            placedOrderList.add(purchaseOrder);
                        }
                    }
                }
            }
        } catch (DBException | SQLException e) {
            throw new ServiceException(e);
        }

        LOG.info("OUT PurchaseOrderDAOImpl : getPurchaseOrderList : Purchase Order count - {}", placedOrderList.size());
        return placedOrderList;
    }
    @Override
    public List<PurchaseOrder> getReceivedOrderList(int manufacturerId) {
        LOG.info("IN PurchaseOrderDAOImpl : getPurchaseOrderList : manufacturerId - {}", manufacturerId);
        List<PurchaseOrder> receivedOrderList = new ArrayList<>();
        try (Connection connection = getDBConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("select * from purchase_orders where manufacturer_id = ? and order_status = 'Received'" )) {
                statement.setInt(1, manufacturerId);
                try (ResultSet rs = statement.executeQuery()) {
                    if (rs != null) {
                        while (rs.next()) {
                            PurchaseOrder purchaseOrder = new PurchaseOrder();
                            purchaseOrder.setOrderid(rs.getString("order_number"));
                            purchaseOrder.setCost(rs.getDouble("total_cost"));
                            purchaseOrder.setDate(rs.getString("order_received_date"));
                            receivedOrderList.add(purchaseOrder);
                        }
                    }
                }
            }
        } catch (DBException | SQLException e) {
            throw new ServiceException(e);
        }

        LOG.info("OUT PurchaseOrderDAOImpl : getPurchaseOrderList : Purchase Order count - {}", receivedOrderList.size());
        return receivedOrderList;
    }
    @Override
    public void placePurchaseOrder(String orderid) {
        LOG.info("IN PurchaseOrderDAOImpl : placePurchaseOrder : orderid - {}", orderid);
        try (Connection connection = getDBConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("update purchase_orders set order_status = 'Placed', order_placed_date = current_timestamp() where order_number = ?" )) {
                statement.setString(1, orderid);
                statement.executeUpdate();
            }
        } catch (DBException | SQLException e) {
            throw new ServiceException(e);
        }
        LOG.info("OUT PurchaseOrderDAOImpl : placePurchaseOrder");
    }
    @Override
    public void receivePurchaseOrder(String orderid) {
        LOG.info("IN PurchaseOrderDAOImpl : receivePurchaseOrder : orderid - {}", orderid);
        try (Connection connection = getDBConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("update purchase_orders set order_status = 'Received', order_received_date = current_timestamp() where order_number = ?" )) {
                statement.setString(1, orderid);
                statement.executeUpdate();
            }
        } catch (DBException | SQLException e) {
            throw new ServiceException(e);
        }
        LOG.info("OUT PurchaseOrderDAOImpl : receivePurchaseOrder");
    }
    @Override
    public void reorderPurchaseOrder(String orderid) {
        LOG.info("IN PurchaseOrderDAOImpl : reorderPurchaseOrder : orderid - {}", orderid);
        try (Connection connection = getDBConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("update purchase_orders set order_status = 'Received' where order_number = ?")) {
                statement.setString(1, orderid);
                statement.executeUpdate();
            }
        } catch (DBException | SQLException e) {
            throw new ServiceException(e);
        }
        LOG.info("OUT PurchaseOrderDAOImpl : receivePurchaseOrder");
    }
    @Override
    public void deletePurchaseOrder(String orderid){

    }

}
