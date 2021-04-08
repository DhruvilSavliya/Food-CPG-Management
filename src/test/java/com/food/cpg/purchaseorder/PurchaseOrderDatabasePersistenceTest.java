package com.food.cpg.purchaseorder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.food.cpg.salesorder.SalesOrderDatabasePersistence;
import com.food.cpg.salesorder.SalesOrderStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.food.cpg.databasepersistence.ICommonDatabaseOperation;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PurchaseOrderDatabasePersistenceTest {

    private static final String TEST_PURCHASE_ORDER_NUMBER = "PO-123";
    private static final Integer TEST_PURCHASE_ORDER_VENDOR_ID = 1;
    private static final Integer TEST_PURCHASE_ORDER_MANUFACTURER_ID = 1;
    private static final Double TEST_PURCHASE_ORDER_COST = 10.0;

    @Mock
    ICommonDatabaseOperation commonDatabaseOperation;

    @Mock
    IPurchaseOrder purchaseOrder;

    @Mock
    Connection connection;

    @Mock
    PreparedStatement preparedStatement;

    @Mock
    ResultSet resultSet;

    @Before
    public void setUp() throws SQLException {
        when(commonDatabaseOperation.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
    }

    @Test
    public void saveTest() throws SQLException {
        doNothing().when(commonDatabaseOperation).executeUpdate(anyString(), anyList());
        when(purchaseOrder.getOrderNumber()).thenReturn(TEST_PURCHASE_ORDER_NUMBER);
        when(purchaseOrder.getVendorId()).thenReturn(TEST_PURCHASE_ORDER_VENDOR_ID);
        when(purchaseOrder.getTotalCost()).thenReturn(TEST_PURCHASE_ORDER_COST);
        when(purchaseOrder.getManufacturerId()).thenReturn(TEST_PURCHASE_ORDER_MANUFACTURER_ID);

        PurchaseOrderDatabasePersistence purchaseOrderDatabasePersistence = new PurchaseOrderDatabasePersistence(commonDatabaseOperation);

        purchaseOrderDatabasePersistence.save(purchaseOrder);

        verify(commonDatabaseOperation, times(1)).executeUpdate(anyString(), anyList());
        verify(purchaseOrder, times(1)).getOrderNumber();
        verify(purchaseOrder, times(1)).getVendorId();
        verify(purchaseOrder, times(1)).getTotalCost();
        verify(purchaseOrder, times(1)).getManufacturerId();
    }

    @Test
    public void getOpenPurchaseOrder() throws SQLException {
        when(resultSet.next()).thenReturn(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
        doNothing().when(commonDatabaseOperation).loadPlaceholderValues(anyObject(), anyList());

        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(1);
        placeholderValues.add(PurchaseOrderStatus.Status.OPEN.name());

        PurchaseOrderDatabasePersistence purchaseOrderDatabasePersistence = new PurchaseOrderDatabasePersistence(commonDatabaseOperation);

        purchaseOrderDatabasePersistence.getOpenPurchaseOrder(1);

        verify(commonDatabaseOperation, times(1)).getConnection();
        verify(connection, times(1)).prepareStatement(anyString());
        verify(commonDatabaseOperation, times(1)).loadPlaceholderValues(preparedStatement, placeholderValues);
        verify(preparedStatement, times(1)).executeQuery();
        verify(resultSet, times(3)).next();
        verify(resultSet, times(4)).getString(anyString());
        verify(resultSet, times(2)).getDouble(anyString());
        verify(resultSet, times(2)).getTimestamp(anyString());
    }

    @Test
    public void getPlacedPurchaseOrder() throws SQLException {
        when(resultSet.next()).thenReturn(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
        doNothing().when(commonDatabaseOperation).loadPlaceholderValues(anyObject(), anyList());

        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(1);
        placeholderValues.add(PurchaseOrderStatus.Status.PLACED.name());

        PurchaseOrderDatabasePersistence purchaseOrderDatabasePersistence = new PurchaseOrderDatabasePersistence(commonDatabaseOperation);

        purchaseOrderDatabasePersistence.getPlacedPurchaseOrder(1);

        verify(commonDatabaseOperation, times(1)).getConnection();
        verify(connection, times(1)).prepareStatement(anyString());
        verify(commonDatabaseOperation, times(1)).loadPlaceholderValues(preparedStatement, placeholderValues);
        verify(preparedStatement, times(1)).executeQuery();
        verify(resultSet, times(3)).next();
        verify(resultSet, times(4)).getString(anyString());
        verify(resultSet, times(2)).getDouble(anyString());
        verify(resultSet, times(2)).getTimestamp(anyString());
    }

    @Test
    public void getReceivedPurchaseOrder() throws SQLException {
        when(resultSet.next()).thenReturn(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
        doNothing().when(commonDatabaseOperation).loadPlaceholderValues(anyObject(), anyList());

        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(1);
        placeholderValues.add(PurchaseOrderStatus.Status.RECEIVED.name());

        PurchaseOrderDatabasePersistence purchaseOrderDatabasePersistence = new PurchaseOrderDatabasePersistence(commonDatabaseOperation);

        purchaseOrderDatabasePersistence.getReceivedPurchaseOrder(1);

        verify(commonDatabaseOperation, times(1)).getConnection();
        verify(connection, times(1)).prepareStatement(anyString());
        verify(commonDatabaseOperation, times(1)).loadPlaceholderValues(preparedStatement, placeholderValues);
        verify(preparedStatement, times(1)).executeQuery();
        verify(resultSet, times(3)).next();
        verify(resultSet, times(4)).getString(anyString());
        verify(resultSet, times(2)).getDouble(anyString());
        verify(resultSet, times(2)).getTimestamp(anyString());
    }

    @Test
    public void loadTest() throws SQLException {
        when(resultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        doNothing().when(commonDatabaseOperation).loadPlaceholderValues(anyObject(), anyList());
        when(purchaseOrder.getOrderNumber()).thenReturn(TEST_PURCHASE_ORDER_NUMBER);

        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(TEST_PURCHASE_ORDER_NUMBER);

        PurchaseOrderDatabasePersistence purchaseOrderDatabasePersistence = new PurchaseOrderDatabasePersistence(commonDatabaseOperation);

        purchaseOrderDatabasePersistence.load(purchaseOrder);

        verify(commonDatabaseOperation, times(1)).getConnection();
        verify(connection, times(1)).prepareStatement(anyString());
        verify(commonDatabaseOperation, times(1)).loadPlaceholderValues(preparedStatement, placeholderValues);
        verify(preparedStatement, times(1)).executeQuery();
        verify(resultSet, times(1)).next();
        verify(resultSet, times(2)).getString(anyString());
        verify(resultSet, times(1)).getDouble(anyString());
        verify(resultSet, times(1)).getTimestamp(anyString());
    }

    @Test
    public void deleteTest() throws SQLException {
        doNothing().when(commonDatabaseOperation).executeUpdate(anyString(), anyList());

        PurchaseOrderDatabasePersistence purchaseOrderDatabasePersistence = new PurchaseOrderDatabasePersistence(commonDatabaseOperation);

        purchaseOrderDatabasePersistence.delete(purchaseOrder);

        verify(commonDatabaseOperation, times(1)).executeUpdate(anyString(), anyList());
    }
}
