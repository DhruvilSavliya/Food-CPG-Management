package com.food.cpg.manufacturingorder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.food.cpg.manufacturer.ManufacturerDatabasePersistence;
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
public class ManufactureOrderDatabasePersistenceTest {

    private static final String TEST_ORDER_NUMBER = "MO-123456";
    private static final Integer TEST_MANUFACTURE_ID = 1;

    @Mock
    ICommonDatabaseOperation commonDatabaseOperation;

    @Mock
    Connection connection;

    @Mock
    PreparedStatement preparedStatement;

    @Mock
    ResultSet resultSet;

    @Mock
    ManufactureOrder manufactureOrder;

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
        placeholderValues.add(ManufactureOrderStatus.Status.OPEN.name());

        ManufactureOrderDatabasePersistence manufactureOrderDatabasePersistence = new ManufactureOrderDatabasePersistence(commonDatabaseOperation);

        manufactureOrderDatabasePersistence.getAllOpenOrders(TEST_MANUFACTURE_ID);

        verify(commonDatabaseOperation, times(1)).getConnection();
        verify(connection, times(1)).prepareStatement(anyString());
        verify(commonDatabaseOperation, times(1)).loadPlaceholderValues(preparedStatement, placeholderValues);
        verify(preparedStatement, times(1)).executeQuery();
        verify(resultSet, times(3)).next();
        verify(resultSet, times(6)).getString(anyString());
        verify(resultSet, times(2)).getInt(anyString());
        verify(resultSet, times(6)).getDouble(anyString());
        verify(resultSet, times(2)).getTimestamp(anyString());
    }

    @Test
    public void getAllManufacturedOrdersTest() throws SQLException {
        when(resultSet.next()).thenReturn(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
        doNothing().when(commonDatabaseOperation).loadPlaceholderValues(anyObject(), anyList());

        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(1);
        placeholderValues.add(ManufactureOrderStatus.Status.MANUFACTURED.name());

        ManufactureOrderDatabasePersistence manufactureOrderDatabasePersistence = new ManufactureOrderDatabasePersistence(commonDatabaseOperation);

        manufactureOrderDatabasePersistence.getAllManufacturedOrders(TEST_MANUFACTURE_ID);

        verify(commonDatabaseOperation, times(1)).getConnection();
        verify(connection, times(1)).prepareStatement(anyString());
        verify(commonDatabaseOperation, times(1)).loadPlaceholderValues(preparedStatement, placeholderValues);
        verify(preparedStatement, times(1)).executeQuery();
        verify(resultSet, times(3)).next();
        verify(resultSet, times(6)).getString(anyString());
        verify(resultSet, times(2)).getInt(anyString());
        verify(resultSet, times(6)).getDouble(anyString());
        verify(resultSet, times(2)).getTimestamp(anyString());
    }

    @Test
    public void getAllPackagedOrdersTest() throws SQLException {
        when(resultSet.next()).thenReturn(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
        doNothing().when(commonDatabaseOperation).loadPlaceholderValues(anyObject(), anyList());

        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(1);
        placeholderValues.add(ManufactureOrderStatus.Status.PACKAGED.name());

        ManufactureOrderDatabasePersistence manufactureOrderDatabasePersistence = new ManufactureOrderDatabasePersistence(commonDatabaseOperation);

        manufactureOrderDatabasePersistence.getAllPackagedOrders(TEST_MANUFACTURE_ID);

        verify(commonDatabaseOperation, times(1)).getConnection();
        verify(connection, times(1)).prepareStatement(anyString());
        verify(commonDatabaseOperation, times(1)).loadPlaceholderValues(preparedStatement, placeholderValues);
        verify(preparedStatement, times(1)).executeQuery();
        verify(resultSet, times(3)).next();
        verify(resultSet, times(6)).getString(anyString());
        verify(resultSet, times(2)).getInt(anyString());
        verify(resultSet, times(6)).getDouble(anyString());
        verify(resultSet, times(2)).getTimestamp(anyString());
    }

    @Test
    public void loadTest() throws SQLException {
        when(resultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        doNothing().when(commonDatabaseOperation).loadPlaceholderValues(anyObject(), anyList());
        when(manufactureOrder.getOrderNumber()).thenReturn(TEST_ORDER_NUMBER);

        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(TEST_ORDER_NUMBER);

        ManufactureOrderDatabasePersistence manufactureOrderDatabasePersistence = new ManufactureOrderDatabasePersistence(commonDatabaseOperation);

        manufactureOrderDatabasePersistence.load(manufactureOrder);

        verify(commonDatabaseOperation, times(1)).getConnection();
        verify(connection, times(1)).prepareStatement(anyString());
        verify(commonDatabaseOperation, times(1)).loadPlaceholderValues(preparedStatement, placeholderValues);
        verify(preparedStatement, times(1)).executeQuery();
        verify(resultSet, times(1)).next();
        verify(resultSet, times(3)).getString(anyString());
        verify(resultSet, times(1)).getInt(anyString());
        verify(resultSet, times(3)).getDouble(anyString());
        verify(resultSet, times(1)).getTimestamp(anyString());
    }

    @Test
    public void deleteTest() throws SQLException {
        doNothing().when(commonDatabaseOperation).executeUpdate(anyString(), anyList());

        ManufactureOrderDatabasePersistence manufactureOrderDatabasePersistence = new ManufactureOrderDatabasePersistence(commonDatabaseOperation);

        manufactureOrderDatabasePersistence.delete(TEST_ORDER_NUMBER);

        verify(commonDatabaseOperation, times(1)).executeUpdate(anyString(), anyList());
    }


}
