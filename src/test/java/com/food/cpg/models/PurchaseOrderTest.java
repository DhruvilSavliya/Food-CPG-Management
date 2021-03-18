package com.food.cpg.models;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * @author P.M
 */

@RunWith(MockitoJUnitRunner.class)
public class PurchaseOrderTest {

    @Test
    public void isValidOrderidTest() {
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setOrderid("");

        boolean isValidOrderid = purchaseOrder.isValidPurchaseOrder();

        Assert.assertFalse(isValidOrderid);
        Assert.assertFalse(purchaseOrder.getErrors().isEmpty());
        Assert.assertNotNull(purchaseOrder.getErrors().get("orderid"));
    }

    @Test
    public void isValidStatusTest() {
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setStatus("");

        boolean isValidStatus = purchaseOrder.isValidPurchaseOrder();

        Assert.assertFalse(isValidStatus);
        Assert.assertFalse(purchaseOrder.getErrors().isEmpty());
        Assert.assertNotNull(purchaseOrder.getErrors().get("status"));
    }

    @Test
    public void isValidDateTest() {
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setDate("");

        boolean isValidDate = purchaseOrder.isValidPurchaseOrder();

        Assert.assertFalse(isValidDate);
        Assert.assertFalse(purchaseOrder.getErrors().isEmpty());
        Assert.assertNotNull(purchaseOrder.getErrors().get("date"));
    }
}

