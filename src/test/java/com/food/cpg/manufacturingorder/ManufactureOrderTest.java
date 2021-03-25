package com.food.cpg.manufacturingorder;


import com.food.cpg.purchaseorder.IPurchaseOrderPersistence;
import com.food.cpg.purchaseorder.PurchaseOrder;
import com.food.cpg.purchaseorder.PurchaseOrderRawMaterial;
import org.junit.Assert;
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
@PrepareForTest(ManufactureOrder.class)
public class ManufactureOrderTest {

    private static final String ORDER_NUMBER_PREFIX = "MO-";
    private static final String GET_PERSISTENCE_METHOD_NAME = "getPersistence";
    private static final String GET_MANUFACTURER_ID_METHOD_NAME = "getLoggedInManufacturerId";

    @Mock
    IManufactureOrderPersistence manufactureOrderPersistence;

    @Test
    public void generateOrderNumberTest() throws Exception {
        ManufactureOrder manufactureOrder = new ManufactureOrder();

        Assert.assertNotNull(manufactureOrder.getOrderNumber());
        Assert.assertTrue(manufactureOrder.getOrderNumber().contains(ORDER_NUMBER_PREFIX));
    }

    @Test
    public void saveTest() throws Exception {
        ManufactureOrder manufactureOrder = spy(new ManufactureOrder());
        manufactureOrder.setManufacturerId(1);

        PowerMockito.doReturn(manufactureOrderPersistence).when(manufactureOrder, GET_PERSISTENCE_METHOD_NAME);
        PowerMockito.doNothing().when(manufactureOrderPersistence).save(manufactureOrder);
        PowerMockito.doReturn(1).when(manufactureOrder, GET_MANUFACTURER_ID_METHOD_NAME);

        manufactureOrder.save();
        verifyPrivate(manufactureOrder).invoke(GET_PERSISTENCE_METHOD_NAME);
        verify(manufactureOrderPersistence, times(1)).save(manufactureOrder);
    }

//    @Test
//    public void calculateTotalCostTest() throws Exception{
//        ManufactureOrder manufactureOrder = spy(new ManufactureOrder());
//
//    }


}