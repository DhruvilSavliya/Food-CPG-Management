package com.food.cpg.inventory;

import com.food.cpg.databasepersistence.ICommonDatabaseOperation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;


@RunWith(MockitoJUnitRunner.class)
public class ItemInventoryDatabasePersistenceTest {

    private static final Integer TEST_ITEM_ID = 10;
    private static final Double TEST_ITEM_QUANTITY = 2.00;

    @Mock
    ICommonDatabaseOperation commonDatabaseOperation;


    @Mock
    ItemInventory itemInventory;


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
