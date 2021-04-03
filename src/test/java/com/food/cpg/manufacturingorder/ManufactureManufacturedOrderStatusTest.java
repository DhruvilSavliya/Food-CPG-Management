package com.food.cpg.manufacturingorder;

import com.food.cpg.inventory.ItemInventory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ManufactureManufacturedOrderStatus.class)
public class ManufactureManufacturedOrderStatusTest  {

    private static final String GET_PERSISTENCE_METHOD_NAME = "getPersistence";
    private static final String GET_ITEM_INVENTORY_INSTANCE_METHOD_NAME = "getItemInventoryInstance";
    private static final String TEST_ORDER_NO = "Test order no";

    @Mock
    IManufactureOrderPersistence manufactureOrderPersistence;

    @Test
    public void getOrderStatusTest() {
        ManufactureManufacturedOrderStatus manufactureManufacturedOrderStatus = new ManufactureManufacturedOrderStatus();

        Assert.assertEquals(ManufactureOrderStatus.Status.MANUFACTURED, manufactureManufacturedOrderStatus.getOrderStatus());
    }

    @Test
    public void moveOrderTest() throws Exception {
        ManufactureManufacturedOrderStatus manufactureManufacturedOrderStatus = spy(new ManufactureManufacturedOrderStatus());
        ManufactureOrder manufactureOrder = new ManufactureOrder();
        ItemInventory itemInventory = spy(new ItemInventory());
        manufactureOrder.setItemId(1);
        manufactureOrder.setItemQuantity(2.00);
        manufactureOrder.setOrderNumber(TEST_ORDER_NO);
//        itemInventory.setItemId(manufactureOrder.getItemId());
//        itemInventory.setItemQuantity(manufactureOrder.getItemQuantity());

        PowerMockito.doNothing().when(itemInventory).increaseQuantity();
        PowerMockito.doReturn(itemInventory).when(manufactureManufacturedOrderStatus,GET_ITEM_INVENTORY_INSTANCE_METHOD_NAME );
        PowerMockito.doReturn(manufactureOrderPersistence).when(manufactureManufacturedOrderStatus, GET_PERSISTENCE_METHOD_NAME);
        PowerMockito.doNothing().when(manufactureOrderPersistence).changeStatus(anyString(), anyString());

        manufactureManufacturedOrderStatus.moveOrder(manufactureOrder);
        verify(manufactureOrderPersistence, times(1)).changeStatus(anyString(), anyString());
    }

}