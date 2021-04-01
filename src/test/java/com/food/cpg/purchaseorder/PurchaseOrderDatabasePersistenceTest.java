package com.food.cpg.purchaseorder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
    private static final Integer TEST_ITEM_ID = 10;

    @Mock
    ICommonDatabaseOperation commonDatabaseOperation;

    @Mock
    PurchaseOrder purchaseOrder;

    @Mock
    Connection connection;

    @Mock
    PreparedStatement preparedStatement;

    @Mock
    ResultSet resultSet;

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
}
