package com.food.cpg.inventory;

import com.food.cpg.databasepersistence.ICommonDatabaseOperation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;


@RunWith(MockitoJUnitRunner.class)
public class ItemInventoryDatabasePersistenceTest {

    private static final Integer TEST_ITEM_ID = 10;
    private static final Integer TEST_ITEM_QUANTITY = 2;

    @Mock
    ICommonDatabaseOperation commonDatabaseOperation;

    @Mock
    Connection connection;

    @Mock
    PreparedStatement preparedStatement;

    @Mock
    ResultSet resultSet;

    @Mock
    ItemInventory itemInventory;

    @Before
    public void setUp() throws SQLException {
        when(commonDatabaseOperation.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
    }

    @Test
    public void increaseQuantityTest() throws SQLException {
        doNothing().when(commonDatabaseOperation).executeUpdate(anyString(), anyList());
        when(itemInventory.getItemId()).thenReturn(TEST_ITEM_ID);
        when(itemInventory.getItemQuantity()).thenReturn(TEST_ITEM_QUANTITY);

        ItemInventoryDatabasePersistence itemInventoryDatabasePersistence = new ItemInventoryDatabasePersistence(commonDatabaseOperation);

        itemInventoryDatabasePersistence.increaseQuantity(itemInventory);

        verify(commonDatabaseOperation, times(1)).executeUpdate(anyString(), anyList());
        verify(itemInventory, times(1)).getItemId();
        verify(itemInventory, times(1)).getItemQuantity();
    }

    @Test
    public void decreaseQuantityTest() throws SQLException {
        doNothing().when(commonDatabaseOperation).executeUpdate(anyString(), anyList());
        when(itemInventory.getItemId()).thenReturn(TEST_ITEM_ID);
        when(itemInventory.getItemQuantity()).thenReturn(TEST_ITEM_QUANTITY);

        ItemInventoryDatabasePersistence itemInventoryDatabasePersistence = new ItemInventoryDatabasePersistence(commonDatabaseOperation);

        itemInventoryDatabasePersistence.decreaseQuantity(itemInventory);

        verify(commonDatabaseOperation, times(1)).executeUpdate(anyString(), anyList());
        verify(itemInventory, times(1)).getItemId();
        verify(itemInventory, times(1)).getItemQuantity();
    }

}
