package com.food.cpg.purchaseorder;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;


import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PurchaseOrder.class)
public class PurchaseOrderTest {

    private static final double DELTA = 1e-15;
    private static final String ORDER_NUMBER_PREFIX = "PO-";
    private static final String TEST_ORDER_NUMBER = "PO-123456";
    private static final String GET_PERSISTENCE_METHOD_NAME = "getPersistence";
    private static final String GET_MANUFACTURER_ID_METHOD_NAME = "getLoggedInManufacturerId";
    private static final int TEST_MANUFACTURER_ID = 1;
    private static final double TEST_PURCHASE_ORDER_COST = 100.0;

    @Mock
    IPurchaseOrderPersistence purchaseOrderPersistence;

    @Mock
    PurchaseOrderRawMaterial purchaseOrderRawMaterial;

    @Mock
    PurchaseOrderStatus purchaseOrderStatus;

    @Test
    public void addPurchaseOrderRawMaterialsTest() {
        IPurchaseOrder purchaseOrder = new PurchaseOrder();
        String autoGeneratedOrderNumber = purchaseOrder.getOrderNumber();

        Assert.assertNull(purchaseOrder.getPurchaseOrderRawMaterials());

        PurchaseOrderRawMaterial purchaseOrderRawMaterial = new PurchaseOrderRawMaterial();
        purchaseOrderRawMaterial.setRawMaterialCost(10.0);

        purchaseOrder.addPurchaseOrderRawMaterials(purchaseOrderRawMaterial);

        List<PurchaseOrderRawMaterial> purchaseOrderRawMaterials = purchaseOrder.getPurchaseOrderRawMaterials();
        Assert.assertNotNull(purchaseOrderRawMaterials);
        Assert.assertEquals(1, purchaseOrderRawMaterials.size());
        Assert.assertEquals(autoGeneratedOrderNumber, purchaseOrderRawMaterials.get(0).getPurchaseOrderNumber());
    }

    @Test
    public void generateOrderNumberTest() throws Exception {
        IPurchaseOrder purchaseOrder = new PurchaseOrder();

        Assert.assertNotNull(purchaseOrder.getOrderNumber());
        Assert.assertTrue(purchaseOrder.getOrderNumber().contains(ORDER_NUMBER_PREFIX));
    }

    @Test
    public void saveTest() throws Exception {
        IPurchaseOrder purchaseOrder = spy(new PurchaseOrder());
        purchaseOrder.setManufacturerId(1);
        purchaseOrder.addPurchaseOrderRawMaterials(purchaseOrderRawMaterial);

        PowerMockito.doReturn(purchaseOrderPersistence).when(purchaseOrder, GET_PERSISTENCE_METHOD_NAME);
        PowerMockito.doNothing().when(purchaseOrderPersistence).save(purchaseOrder);
        PowerMockito.doReturn(1).when(purchaseOrder, GET_MANUFACTURER_ID_METHOD_NAME);
        PowerMockito.doNothing().when(purchaseOrderRawMaterial).save();

        purchaseOrder.save();
        verifyPrivate(purchaseOrder).invoke(GET_PERSISTENCE_METHOD_NAME);
        verify(purchaseOrderPersistence, times(1)).save(purchaseOrder);
        verify(purchaseOrderRawMaterial, times(1)).save();
    }

    @Test
    public void calculateTotalCostTest() {
        IPurchaseOrder purchaseOrder = spy(new PurchaseOrder());

        IPurchaseOrderRawMaterial purchaseOrderRawMaterial = new PurchaseOrderRawMaterial();
        purchaseOrderRawMaterial.setRawMaterialCost(10.0);

        List<IPurchaseOrderRawMaterial> purchaseOrderRawMaterials = new ArrayList<>();
        purchaseOrderRawMaterials.add(purchaseOrderRawMaterial);

        PowerMockito.doReturn(purchaseOrderRawMaterials).when(purchaseOrder).getPurchaseOrderRawMaterials();

        purchaseOrder.calculateTotalCost();

        Assert.assertEquals(10.0, purchaseOrder.getTotalCost(), DELTA);
    }

    @Test
    public void getAllOpenOrdersTest() throws Exception {
        PurchaseOrder purchaseOrder = spy(new PurchaseOrder());
        purchaseOrder.setManufacturerId(TEST_MANUFACTURER_ID);
        purchaseOrder.setTotalCost(TEST_PURCHASE_ORDER_COST);

        List<PurchaseOrder> purchaseOrders = new ArrayList<>();
        purchaseOrders.add(purchaseOrder);

        PowerMockito.doReturn(purchaseOrderPersistence).when(purchaseOrder, GET_PERSISTENCE_METHOD_NAME);
        PowerMockito.doReturn(purchaseOrders).when(purchaseOrderPersistence).getOpenPurchaseOrder(anyInt());
        PowerMockito.doReturn(1).when(purchaseOrder, GET_MANUFACTURER_ID_METHOD_NAME);

        List<IPurchaseOrder> purchaseOrdersResult = purchaseOrder.getAllOpenOrders();

        verifyPrivate(purchaseOrder).invoke(GET_PERSISTENCE_METHOD_NAME);
        verifyPrivate(purchaseOrder).invoke(GET_MANUFACTURER_ID_METHOD_NAME);
        verify(purchaseOrderPersistence, times(1)).getOpenPurchaseOrder(anyInt());
        Assert.assertNotNull(purchaseOrdersResult);
        Assert.assertEquals(1, purchaseOrdersResult.size());
        Assert.assertEquals(TEST_MANUFACTURER_ID, purchaseOrdersResult.get(0).getManufacturerId().intValue());
        Assert.assertEquals(TEST_PURCHASE_ORDER_COST, purchaseOrdersResult.get(0).getTotalCost(), DELTA);
    }

    @Test
    public void getAllPlacedOrdersTest() throws Exception {
        PurchaseOrder purchaseOrder = spy(new PurchaseOrder());
        purchaseOrder.setManufacturerId(TEST_MANUFACTURER_ID);
        purchaseOrder.setTotalCost(TEST_PURCHASE_ORDER_COST);

        List<PurchaseOrder> purchaseOrders = new ArrayList<>();
        purchaseOrders.add(purchaseOrder);

        PowerMockito.doReturn(purchaseOrderPersistence).when(purchaseOrder, GET_PERSISTENCE_METHOD_NAME);
        PowerMockito.doReturn(purchaseOrders).when(purchaseOrderPersistence).getPlacedPurchaseOrder(anyInt());
        PowerMockito.doReturn(1).when(purchaseOrder, GET_MANUFACTURER_ID_METHOD_NAME);

        List<IPurchaseOrder> purchaseOrdersResult = purchaseOrder.getAllPlacedOrders();

        verifyPrivate(purchaseOrder).invoke(GET_PERSISTENCE_METHOD_NAME);
        verifyPrivate(purchaseOrder).invoke(GET_MANUFACTURER_ID_METHOD_NAME);
        verify(purchaseOrderPersistence, times(1)).getPlacedPurchaseOrder(anyInt());
        Assert.assertNotNull(purchaseOrdersResult);
        Assert.assertEquals(1, purchaseOrdersResult.size());
        Assert.assertEquals(TEST_MANUFACTURER_ID, purchaseOrdersResult.get(0).getManufacturerId().intValue());
        Assert.assertEquals(TEST_PURCHASE_ORDER_COST, purchaseOrdersResult.get(0).getTotalCost(), DELTA);
    }

    @Test
    public void getAllReceivedOrdersTest() throws Exception {
        PurchaseOrder purchaseOrder = spy(new PurchaseOrder());
        purchaseOrder.setManufacturerId(TEST_MANUFACTURER_ID);
        purchaseOrder.setTotalCost(TEST_PURCHASE_ORDER_COST);

        List<PurchaseOrder> purchaseOrders = new ArrayList<>();
        purchaseOrders.add(purchaseOrder);

        PowerMockito.doReturn(purchaseOrderPersistence).when(purchaseOrder, GET_PERSISTENCE_METHOD_NAME);
        PowerMockito.doReturn(purchaseOrders).when(purchaseOrderPersistence).getReceivedPurchaseOrder(anyInt());
        PowerMockito.doReturn(1).when(purchaseOrder, GET_MANUFACTURER_ID_METHOD_NAME);

        List<IPurchaseOrder> purchaseOrdersResult = purchaseOrder.getAllReceivedOrders();

        verifyPrivate(purchaseOrder).invoke(GET_PERSISTENCE_METHOD_NAME);
        verifyPrivate(purchaseOrder).invoke(GET_MANUFACTURER_ID_METHOD_NAME);
        verify(purchaseOrderPersistence, times(1)).getReceivedPurchaseOrder(anyInt());
        Assert.assertNotNull(purchaseOrdersResult);
        Assert.assertEquals(1, purchaseOrdersResult.size());
        Assert.assertEquals(TEST_MANUFACTURER_ID, purchaseOrdersResult.get(0).getManufacturerId().intValue());
        Assert.assertEquals(TEST_PURCHASE_ORDER_COST, purchaseOrdersResult.get(0).getTotalCost(), DELTA);
    }

    @Test
    public void moveOrderToNextStageTest() throws Exception {
        PurchaseOrder purchaseOrder = spy(new PurchaseOrder());
        purchaseOrder.setOrderNumber(TEST_ORDER_NUMBER);
        purchaseOrder.setManufacturerId(TEST_MANUFACTURER_ID);
        purchaseOrder.setPurchaseOrderStatus(purchaseOrderStatus);

        PowerMockito.doNothing().when(purchaseOrderStatus).moveOrder(purchaseOrder);

        purchaseOrder.moveOrderToNextStage();

        verify(purchaseOrderStatus, times(1)).moveOrder(purchaseOrder);
    }

    @Test
    public void deleteTest() throws Exception {
        PurchaseOrder purchaseOrder = spy(new PurchaseOrder());
        purchaseOrder.setOrderNumber(TEST_ORDER_NUMBER);
        purchaseOrder.setManufacturerId(TEST_MANUFACTURER_ID);
        purchaseOrder.setTotalCost(TEST_PURCHASE_ORDER_COST);

        PowerMockito.doReturn(purchaseOrderPersistence).when(purchaseOrder, GET_PERSISTENCE_METHOD_NAME);
        PowerMockito.doNothing().when(purchaseOrderPersistence).delete(purchaseOrder);

        purchaseOrder.delete();

        verifyPrivate(purchaseOrder).invoke(GET_PERSISTENCE_METHOD_NAME);
        verify(purchaseOrderPersistence, times(1)).delete(purchaseOrder);
    }

    @Test
    public void loadTest() throws Exception {
        PurchaseOrder purchaseOrder = spy(new PurchaseOrder());
        purchaseOrder.setManufacturerId(TEST_MANUFACTURER_ID);
        purchaseOrder.setTotalCost(TEST_PURCHASE_ORDER_COST);

        PowerMockito.doReturn(purchaseOrderPersistence).when(purchaseOrder, GET_PERSISTENCE_METHOD_NAME);
        PowerMockito.doNothing().when(purchaseOrderPersistence).load(purchaseOrder);

        purchaseOrder.load();

        verifyPrivate(purchaseOrder).invoke(GET_PERSISTENCE_METHOD_NAME);
        verify(purchaseOrderPersistence, times(1)).load(purchaseOrder);
    }

}
