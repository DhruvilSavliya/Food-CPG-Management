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
@PrepareForTest(SalesPackagedOrderStatus.class)
public class SalesPackagedOrderStatusTest {

    private static final String GET_PERSISTENCE_METHOD_NAME = "getPersistence";

    @Mock
    ISalesOrderPersistence salesOrderPersistence;

    @Test
    public void getOrderStatusTest() {
        SalesPackagedOrderStatus salesPackagedOrderStatus = new SalesPackagedOrderStatus();

        Assert.assertEquals(SalesOrderStatus.Status.PACKAGED, salesPackagedOrderStatus.getOrderStatus());
    }

    @Test
    public void moveOrderTest() throws Exception {
        SalesPackagedOrderStatus salesPackagedOrderStatus = spy(new SalesPackagedOrderStatus());

        PowerMockito.doReturn(salesOrderPersistence).when(salesPackagedOrderStatus, GET_PERSISTENCE_METHOD_NAME);
        PowerMockito.doNothing().when(salesOrderPersistence).changeStatus(anyString(), anyString());

        salesPackagedOrderStatus.moveOrder(anyString());
        verify(salesOrderPersistence, times(1)).changeStatus(anyString(), anyString());
    }
}
