package com.food.cpg.inventory;

import com.food.cpg.vendor.Vendor;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;

@RunWith(PowerMockRunner.class)
@PrepareForTest(RawMaterialInventory.class)
public class RawMaterialInventoryTest extends TestCase {

    public static final String GET_PERSISTENCE_METHOD = "getPersistence";
    public static final String GET_LOGGED_IN_MANUFACTURER_ID_METHOD = "getLoggedInManufacturerId";
    private static final Integer TEST_MANUFACTURER_ID = 1;
    private static final Integer RAW_MATERIAL_ID = 1;
    private static final String RAW_MATERIAL_NAME = "Test raw material";

    @Mock
    IRawMaterialInventoryPersistence rawMaterialInventoryPersistence;

    @Test
    public void getAllTest() throws Exception {
        RawMaterialInventory rawMaterialInventory = spy(new RawMaterialInventory());
        rawMaterialInventory.setManufacturerId(TEST_MANUFACTURER_ID);
        rawMaterialInventory.setRawMaterialId(RAW_MATERIAL_ID);
        rawMaterialInventory.setRawMaterialName(RAW_MATERIAL_NAME);

        List<RawMaterialInventory> rawMaterialInventoryList = new ArrayList<>();
        rawMaterialInventoryList.add(rawMaterialInventory);

        PowerMockito.doReturn(rawMaterialInventoryPersistence).when(rawMaterialInventory, GET_PERSISTENCE_METHOD);
        PowerMockito.doReturn(rawMaterialInventoryList).when(rawMaterialInventoryPersistence).getAll(anyInt());

        List<RawMaterialInventory> rawMaterialInventoriesResult = rawMaterialInventory.getAll();
        Assert.assertNotNull(rawMaterialInventoriesResult);
        Assert.assertEquals(1, rawMaterialInventoriesResult.size());
        Assert.assertEquals(RAW_MATERIAL_NAME, rawMaterialInventoriesResult.get(0).getRawMaterialName());
    }

    @Test
    public void saveTest() throws Exception {
        RawMaterialInventory rawMaterialInventory = spy(new RawMaterialInventory());
        rawMaterialInventory.setManufacturerId(TEST_MANUFACTURER_ID);
        rawMaterialInventory.setRawMaterialName(RAW_MATERIAL_NAME);

        PowerMockito.doReturn(rawMaterialInventoryPersistence).when(rawMaterialInventory, GET_PERSISTENCE_METHOD);
        PowerMockito.doNothing().when(rawMaterialInventoryPersistence).save(rawMaterialInventory);
        PowerMockito.doReturn(1).when(rawMaterialInventory, GET_LOGGED_IN_MANUFACTURER_ID_METHOD);

        rawMaterialInventory.save();
        verifyPrivate(rawMaterialInventory).invoke(GET_PERSISTENCE_METHOD);
        verify(rawMaterialInventoryPersistence, times(1)).save(rawMaterialInventory);
    }

}