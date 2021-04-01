package com.food.cpg.salesorder;

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
@PrepareForTest(SalesOpenOrderStatus.class)
public class SalesOpenOrderStatusTest {

    private static final String GET_PERSISTENCE_METHOD_NAME = "getPersistence";

    @Mock
    ISalesOrderPersistence salesOrderPersistence;

    @Test
    public void getOrderStatusTest() {
        SalesOpenOrderStatus salesOpenOrderStatus = new SalesOpenOrderStatus();

        Assert.assertEquals(SalesOrderStatus.Status.OPEN, salesOpenOrderStatus.getOrderStatus());
    }

    @Test
    public void moveOrderTest() throws Exception {
        SalesOpenOrderStatus salesOpenOrderStatus = spy(new SalesOpenOrderStatus());

        PowerMockito.doReturn(salesOrderPersistence).when(salesOpenOrderStatus, GET_PERSISTENCE_METHOD_NAME);
        PowerMockito.doNothing().when(salesOrderPersistence).changeStatus(anyString(), anyString());

        salesOpenOrderStatus.moveOrder(anyString());
        verify(salesOrderPersistence, times(1)).changeStatus(anyString(), anyString());
    }
}
