package com.food.cpg.inventory;

import com.food.cpg.databasepersistence.ICommonDatabaseOperation;
import com.food.cpg.vendor.VendorDatabasePersistence;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class RawMaterialInventoryDatabasePersistenceTest {

    private static final Integer TEST_RAW_MATERIAL_ID = 1;
    private static final String TEST_RAW_MATERIAL_NAME = "Test raw material";
    private static final Double TEST_RAW_MATERIAL_QUANTITY = 10.0;
    private static final String TEST_RAW_MATERIAL_QUANTITY_UOM = "g";

    @Mock
    ICommonDatabaseOperation commonDatabaseOperation;

    @Mock
    Connection connection;

    @Mock
    PreparedStatement preparedStatement;

    @Mock
    ResultSet resultSet;

    @Mock
    RawMaterialInventory rawMaterialInventory;

    @Before
    public void setUp() throws SQLException {
        when(commonDatabaseOperation.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
    }

    @Test
    public void getAllTest() throws SQLException {
        when(resultSet.next()).thenReturn(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
        doNothing().when(commonDatabaseOperation).loadPlaceholderValues(anyObject(), anyList());

        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(1);

        RawMaterialInventoryDatabasePersistence rawMaterialInventoryDatabasePersistence = new RawMaterialInventoryDatabasePersistence(commonDatabaseOperation);

        rawMaterialInventoryDatabasePersistence.getAll(1);

        verify(commonDatabaseOperation, times(1)).getConnection();
        verify(connection, times(1)).prepareStatement(anyString());
        verify(commonDatabaseOperation, times(1)).loadPlaceholderValues(preparedStatement, placeholderValues);
        verify(preparedStatement, times(1)).executeQuery();
        verify(resultSet, times(3)).next();
        verify(resultSet, times(4)).getString(anyString());
        verify(resultSet, times(2)).getInt(anyString());
        verify(resultSet, times(2)).getDouble(anyString());
    }

    @Test
    public void increaseQuantityTest() throws SQLException {
        doNothing().when(commonDatabaseOperation).executeUpdate(anyString(), anyList());
        when(rawMaterialInventory.getRawMaterialId()).thenReturn(TEST_RAW_MATERIAL_ID);
        when(rawMaterialInventory.getRawMaterialQuantity()).thenReturn(TEST_RAW_MATERIAL_QUANTITY);
        when(rawMaterialInventory.getRawMaterialQuantityUOM()).thenReturn(TEST_RAW_MATERIAL_QUANTITY_UOM);

        RawMaterialInventoryDatabasePersistence rawMaterialInventoryDatabasePersistence = new RawMaterialInventoryDatabasePersistence(commonDatabaseOperation);

        rawMaterialInventoryDatabasePersistence.increaseQuantity(rawMaterialInventory);

        verify(commonDatabaseOperation, times(1)).executeUpdate(anyString(), anyList());
        verify(rawMaterialInventory, times(1)).getRawMaterialId();
        verify(rawMaterialInventory, times(1)).getRawMaterialQuantity();
        verify(rawMaterialInventory, times(1)).getRawMaterialQuantityUOM();
    }

    @Test
    public void decreaseQuantityTest() throws SQLException {
        doNothing().when(commonDatabaseOperation).executeUpdate(anyString(), anyList());
        when(rawMaterialInventory.getRawMaterialId()).thenReturn(TEST_RAW_MATERIAL_ID);
        when(rawMaterialInventory.getRawMaterialQuantity()).thenReturn(TEST_RAW_MATERIAL_QUANTITY);
        when(rawMaterialInventory.getRawMaterialQuantityUOM()).thenReturn(TEST_RAW_MATERIAL_QUANTITY_UOM);

        RawMaterialInventoryDatabasePersistence rawMaterialInventoryDatabasePersistence = new RawMaterialInventoryDatabasePersistence(commonDatabaseOperation);

        rawMaterialInventoryDatabasePersistence.decreaseQuantity(rawMaterialInventory);

        verify(commonDatabaseOperation, times(1)).executeUpdate(anyString(), anyList());
        verify(rawMaterialInventory, times(1)).getRawMaterialId();
        verify(rawMaterialInventory, times(1)).getRawMaterialQuantity();
        verify(rawMaterialInventory, times(1)).getRawMaterialQuantityUOM();
    }


}
