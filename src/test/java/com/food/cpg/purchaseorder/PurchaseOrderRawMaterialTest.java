package com.food.cpg.purchaseorder;

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
@PrepareForTest(PurchaseOrderRawMaterial.class)
public class PurchaseOrderRawMaterialTest {

    private static final String GET_PERSISTENCE_METHOD_NAME = "getPersistence";

    @Mock
    IPurchaseOrderRawMaterialPersistence purchaseOrderRawMaterialPersistence;

    @Test
    public void saveTest() throws Exception {
        PurchaseOrderRawMaterial purchaseOrderRawMaterial = spy(new PurchaseOrderRawMaterial());

        PowerMockito.doReturn(purchaseOrderRawMaterialPersistence).when(purchaseOrderRawMaterial, GET_PERSISTENCE_METHOD_NAME);
        PowerMockito.doNothing().when(purchaseOrderRawMaterialPersistence).save(purchaseOrderRawMaterial);

        purchaseOrderRawMaterial.save();
        verifyPrivate(purchaseOrderRawMaterial).invoke(GET_PERSISTENCE_METHOD_NAME);
        verify(purchaseOrderRawMaterialPersistence, times(1)).save(purchaseOrderRawMaterial);
    }
}
