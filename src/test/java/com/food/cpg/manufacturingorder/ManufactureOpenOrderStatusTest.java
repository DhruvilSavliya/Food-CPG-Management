package com.food.cpg.manufacturingorder;

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


@RunWith(PowerMockRunner.class)
@PrepareForTest(ManufactureOpenOrderStatus.class)
public class ManufactureOpenOrderStatusTest{

    private static final String GET_PERSISTENCE_METHOD_NAME = "getPersistence";

    @Mock
    IManufactureOrderPersistence manufactureOrderPersistence;

    @Test
    public void getOrderStatusTest() {
        ManufactureOpenOrderStatus manufactureOpenOrderStatus = new ManufactureOpenOrderStatus();

        Assert.assertEquals(ManufactureOrderStatus.Status.OPEN, manufactureOpenOrderStatus.getOrderStatus());
    }

    @Test
    public void moveOrderTest() throws Exception {
        ManufactureOpenOrderStatus manufactureOpenOrderStatus = spy(new ManufactureOpenOrderStatus());
        ManufactureOrder manufactureOrder = new ManufactureOrder();
        manufactureOrder.setItemId(1);
        manufactureOrder.setItemQuantity(2.00);

        PowerMockito.doReturn(manufactureOrderPersistence).when(manufactureOpenOrderStatus, GET_PERSISTENCE_METHOD_NAME);
        PowerMockito.doNothing().when(manufactureOrderPersistence).changeStatus(anyString(), anyString());

        manufactureOpenOrderStatus.moveOrder(manufactureOrder);
        verify(manufactureOrderPersistence, times(1)).changeStatus(anyString(), anyString());
    }

}