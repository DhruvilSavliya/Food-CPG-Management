package com.food.cpg.rawmaterial;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.food.cpg.models.Unit;

/**
 * @author Kartik Gevariya
 */
@RunWith(MockitoJUnitRunner.class)
public class RawMaterialTests {

    @Test
    public void isValidRawMaterialNameTest() {
        RawMaterial rawMaterial = new RawMaterial();
        rawMaterial.setName("");

        boolean isValidName = rawMaterial.isValidRawMaterial();

        Assert.assertFalse(isValidName);
        Assert.assertFalse(rawMaterial.getErrors().isEmpty());
        Assert.assertNotNull(rawMaterial.getErrors().get("name"));
    }

    @Test
    public void isValidRawMaterialVendorTest() {
        RawMaterial rawMaterial = new RawMaterial();
        rawMaterial.setVendorId(null);

        boolean isValidVendor = rawMaterial.isValidRawMaterial();

        Assert.assertFalse(isValidVendor);
        Assert.assertFalse(rawMaterial.getErrors().isEmpty());
        Assert.assertNotNull(rawMaterial.getErrors().get("vendor"));
    }

    @Test
    public void isValidRawMaterialUnitCostTest() {
        RawMaterial rawMaterial = new RawMaterial();
        rawMaterial.setUnitCost(null);

        boolean isValidUnitCost = rawMaterial.isValidRawMaterial();

        Assert.assertFalse(isValidUnitCost);
        Assert.assertFalse(rawMaterial.getErrors().isEmpty());
        Assert.assertNotNull(rawMaterial.getErrors().get("unitCost"));
    }

    @Test
    public void isValidRawMaterialUnitMeasurementTest() {
        RawMaterial rawMaterial = new RawMaterial();
        rawMaterial.setUnitMeasurement(null);
        rawMaterial.setUnitMeasurementUOM(Unit.GRAM.getAlias());

        boolean isValidUnitMeasurement = rawMaterial.isValidRawMaterial();

        Assert.assertFalse(isValidUnitMeasurement);
        Assert.assertFalse(rawMaterial.getErrors().isEmpty());
        Assert.assertNotNull(rawMaterial.getErrors().get("unitMeasurement"));
    }

    @Test
    public void isValidRawMaterialUnitMeasurementUOMTest() {
        RawMaterial rawMaterial = new RawMaterial();
        rawMaterial.setUnitMeasurement(20.5);
        rawMaterial.setUnitMeasurementUOM(null);

        boolean isValidUnitMeasurement = rawMaterial.isValidRawMaterial();

        Assert.assertFalse(isValidUnitMeasurement);
        Assert.assertFalse(rawMaterial.getErrors().isEmpty());
        Assert.assertNotNull(rawMaterial.getErrors().get("unitMeasurement"));
    }

    @Test
    public void isValidRawMaterialReorderPointTest() {
        RawMaterial rawMaterial = new RawMaterial();
        rawMaterial.setReorderPointQuantity(null);
        rawMaterial.setReorderPointQuantityUOM(Unit.FL_OZ.getAlias());

        boolean isValidReorderPoint = rawMaterial.isValidRawMaterial();

        Assert.assertFalse(isValidReorderPoint);
        Assert.assertFalse(rawMaterial.getErrors().isEmpty());
        Assert.assertNotNull(rawMaterial.getErrors().get("reorderPointQuantity"));
    }

    @Test
    public void isValidRawMaterialReorderPointUOMTest() {
        RawMaterial rawMaterial = new RawMaterial();
        rawMaterial.setReorderPointQuantity(25.10);
        rawMaterial.setReorderPointQuantityUOM(null);

        boolean isValidReorderPoint = rawMaterial.isValidRawMaterial();

        Assert.assertFalse(isValidReorderPoint);
        Assert.assertFalse(rawMaterial.getErrors().isEmpty());
        Assert.assertNotNull(rawMaterial.getErrors().get("reorderPointQuantity"));
    }
}
