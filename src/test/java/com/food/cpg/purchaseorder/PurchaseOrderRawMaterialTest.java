package com.food.cpg.purchaseorder;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.food.cpg.rawmaterial.RawMaterial;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PurchaseOrderRawMaterial.class)
public class PurchaseOrderRawMaterialTest {

    private static final double DELTA = 1e-15;
    private static final String GET_PERSISTENCE_METHOD_NAME = "getPersistence";

    @Mock
    IPurchaseOrderRawMaterialPersistence purchaseOrderRawMaterialPersistence;

    @Mock
    RawMaterial rawMaterial;

    @Test
    public void saveTest() throws Exception {
        PurchaseOrderRawMaterial purchaseOrderRawMaterial = spy(new PurchaseOrderRawMaterial());

        PowerMockito.doReturn(purchaseOrderRawMaterialPersistence).when(purchaseOrderRawMaterial, GET_PERSISTENCE_METHOD_NAME);
        PowerMockito.doNothing().when(purchaseOrderRawMaterialPersistence).save(purchaseOrderRawMaterial);

        purchaseOrderRawMaterial.save();
        verifyPrivate(purchaseOrderRawMaterial).invoke(GET_PERSISTENCE_METHOD_NAME);
        verify(purchaseOrderRawMaterialPersistence, times(1)).save(purchaseOrderRawMaterial);
    }

    @Test
    public void loadCostTest() {
        PurchaseOrderRawMaterial purchaseOrderRawMaterial = new PurchaseOrderRawMaterial();
        purchaseOrderRawMaterial.setRawMaterialId(1);
        purchaseOrderRawMaterial.setRawMaterialQuantity(10.0);

        when(rawMaterial.getCost(anyInt())).thenReturn(20.0);

        purchaseOrderRawMaterial.loadCost(rawMaterial);

        Assert.assertEquals(200.0, purchaseOrderRawMaterial.getRawMaterialCost(), DELTA);
    }
}
