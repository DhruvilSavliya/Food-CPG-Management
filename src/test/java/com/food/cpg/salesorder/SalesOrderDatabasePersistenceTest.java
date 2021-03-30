package com.food.cpg.salesorder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.food.cpg.databasepersistence.ICommonDatabaseOperation;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SalesOrderDatabasePersistenceTest {

    private static final String TEST_ORDER_NUMBER = "SO-123456";

    @Mock
    ICommonDatabaseOperation commonDatabaseOperation;

    @Mock
    Connection connection;

    @Mock
    PreparedStatement preparedStatement;

    @Mock
    ResultSet resultSet;

    @Mock
    SalesOrder salesOrder;

    @Before
    public void setUp() throws SQLException {
        when(commonDatabaseOperation.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
    }

    @Test
    public void getAllOpenOrdersTest() throws SQLException {
        when(resultSet.next()).thenReturn(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
        doNothing().when(commonDatabaseOperation).loadPlaceholderValues(anyObject(), anyList());

        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(1);
        placeholderValues.add(SalesOrderStatus.Status.OPEN.name());

        SalesOrderDatabasePersistence salesOrderDatabasePersistence = new SalesOrderDatabasePersistence(commonDatabaseOperation);

        salesOrderDatabasePersistence.getAllOpenOrders(1);

        verify(commonDatabaseOperation, times(1)).getConnection();
        verify(connection, times(1)).prepareStatement(anyString());
        verify(commonDatabaseOperation, times(1)).loadPlaceholderValues(preparedStatement, placeholderValues);
        verify(preparedStatement, times(1)).executeQuery();
        verify(resultSet, times(3)).next();
        verify(resultSet, times(4)).getString(anyString());
        verify(resultSet, times(4)).getInt(anyString());
        verify(resultSet, times(2)).getDouble(anyString());
        verify(resultSet, times(2)).getTimestamp(anyString());
    }

    @Test
    public void getAllPackagedOrdersTest() throws SQLException {
        when(resultSet.next()).thenReturn(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
        doNothing().when(commonDatabaseOperation).loadPlaceholderValues(anyObject(), anyList());

        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(1);
        placeholderValues.add(SalesOrderStatus.Status.PACKAGED.name());

        SalesOrderDatabasePersistence salesOrderDatabasePersistence = new SalesOrderDatabasePersistence(commonDatabaseOperation);

        salesOrderDatabasePersistence.getAllPackagedOrders(1);

        verify(commonDatabaseOperation, times(1)).getConnection();
        verify(connection, times(1)).prepareStatement(anyString());
        verify(commonDatabaseOperation, times(1)).loadPlaceholderValues(preparedStatement, placeholderValues);
        verify(preparedStatement, times(1)).executeQuery();
        verify(resultSet, times(3)).next();
        verify(resultSet, times(4)).getString(anyString());
        verify(resultSet, times(4)).getInt(anyString());
        verify(resultSet, times(2)).getDouble(anyString());
        verify(resultSet, times(2)).getTimestamp(anyString());
    }

    @Test
    public void getAllShippedOrdersTest() throws SQLException {
        when(resultSet.next()).thenReturn(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
        doNothing().when(commonDatabaseOperation).loadPlaceholderValues(anyObject(), anyList());

        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(1);
        placeholderValues.add(SalesOrderStatus.Status.SHIPPED.name());

        SalesOrderDatabasePersistence salesOrderDatabasePersistence = new SalesOrderDatabasePersistence(commonDatabaseOperation);

        salesOrderDatabasePersistence.getAllShippedOrders(1);

        verify(commonDatabaseOperation, times(1)).getConnection();
        verify(connection, times(1)).prepareStatement(anyString());
        verify(commonDatabaseOperation, times(1)).loadPlaceholderValues(preparedStatement, placeholderValues);
        verify(preparedStatement, times(1)).executeQuery();
        verify(resultSet, times(3)).next();
        verify(resultSet, times(4)).getString(anyString());
        verify(resultSet, times(4)).getInt(anyString());
        verify(resultSet, times(2)).getDouble(anyString());
        verify(resultSet, times(2)).getTimestamp(anyString());
    }

    @Test
    public void getAllPaidOrdersTest() throws SQLException {
        when(resultSet.next()).thenReturn(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
        doNothing().when(commonDatabaseOperation).loadPlaceholderValues(anyObject(), anyList());

        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(1);
        placeholderValues.add(SalesOrderStatus.Status.PAID.name());

        SalesOrderDatabasePersistence salesOrderDatabasePersistence = new SalesOrderDatabasePersistence(commonDatabaseOperation);

        salesOrderDatabasePersistence.getAllPaidOrders(1);

        verify(commonDatabaseOperation, times(1)).getConnection();
        verify(connection, times(1)).prepareStatement(anyString());
        verify(commonDatabaseOperation, times(1)).loadPlaceholderValues(preparedStatement, placeholderValues);
        verify(preparedStatement, times(1)).executeQuery();
        verify(resultSet, times(3)).next();
        verify(resultSet, times(4)).getString(anyString());
        verify(resultSet, times(4)).getInt(anyString());
        verify(resultSet, times(2)).getDouble(anyString());
        verify(resultSet, times(2)).getTimestamp(anyString());
    }

    @Test
    public void loadTest() throws SQLException {
        when(resultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        doNothing().when(commonDatabaseOperation).loadPlaceholderValues(anyObject(), anyList());
        when(salesOrder.getOrderNumber()).thenReturn(TEST_ORDER_NUMBER);

        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(TEST_ORDER_NUMBER);

        SalesOrderDatabasePersistence salesOrderDatabasePersistence = new SalesOrderDatabasePersistence(commonDatabaseOperation);

        salesOrderDatabasePersistence.load(salesOrder);

        verify(commonDatabaseOperation, times(1)).getConnection();
        verify(connection, times(1)).prepareStatement(anyString());
        verify(commonDatabaseOperation, times(1)).loadPlaceholderValues(preparedStatement, placeholderValues);
        verify(preparedStatement, times(1)).executeQuery();
        verify(resultSet, times(1)).next();
        verify(resultSet, times(2)).getString(anyString());
        verify(resultSet, times(2)).getInt(anyString());
        verify(resultSet, times(1)).getDouble(anyString());
        verify(resultSet, times(1)).getTimestamp(anyString());
    }

    @Test
    public void deleteTest() throws SQLException {
        doNothing().when(commonDatabaseOperation).executeUpdate(anyString(), anyList());

        SalesOrderDatabasePersistence salesOrderDatabasePersistence = new SalesOrderDatabasePersistence(commonDatabaseOperation);

        salesOrderDatabasePersistence.delete(TEST_ORDER_NUMBER);

        verify(commonDatabaseOperation, times(1)).executeUpdate(anyString(), anyList());
    }
}
