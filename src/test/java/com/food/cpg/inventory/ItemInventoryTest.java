package com.food.cpg.inventory;


import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ItemInventory.class)
public class ItemInventoryTest extends TestCase {

    public static final String GET_PERSISTENCE_METHOD = "getPersistence";

    @Mock
    IItemInventoryPersistence itemInventoryPersistence;

    @Test
    public void increaseQuantityTest() throws Exception {
        ItemInventory itemInventory = spy(new ItemInventory());

        PowerMockito.doReturn(itemInventoryPersistence).when(itemInventory, GET_PERSISTENCE_METHOD);
        PowerMockito.doNothing().when(itemInventoryPersistence).increaseQuantity(itemInventory);

        itemInventory.increaseQuantity();
        verifyPrivate(itemInventory).invoke(GET_PERSISTENCE_METHOD);
        verify(itemInventoryPersistence, times(1)).increaseQuantity(itemInventory);
    }

    @Test
    public void decreaseQuantityTest() throws Exception {
        ItemInventory itemInventory = spy(new ItemInventory());

        PowerMockito.doReturn(itemInventoryPersistence).when(itemInventory, GET_PERSISTENCE_METHOD);
        PowerMockito.doNothing().when(itemInventoryPersistence).decreaseQuantity(itemInventory);

        itemInventory.decreaseQuantity();
        verifyPrivate(itemInventory).invoke(GET_PERSISTENCE_METHOD);
        verify(itemInventoryPersistence, times(1)).decreaseQuantity(itemInventory);
    }

}
