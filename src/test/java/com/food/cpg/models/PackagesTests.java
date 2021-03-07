package com.food.cpg.models;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * @author Dhruvilkumar Savliya
 */
@RunWith(MockitoJUnitRunner.class)
public class PackagesTests {

    @Test
    public void isValidPackageNameTest() {
        Packages packages = new Packages();
        packages.setPackageName("");

        boolean isValidName = packages.isValidPackage();

        Assert.assertFalse(isValidName);
        Assert.assertFalse(packages.getErrors().isEmpty());
        Assert.assertNotNull(packages.getErrors().get("packageName"));
    }

    @Test
    public void isValidPackageItemTest() {
        Packages packages = new Packages();
        packages.setRawMaterialId(null);

        boolean isValidItem = packages.isValidPackage();

        Assert.assertFalse(isValidItem);
        Assert.assertFalse(packages.getErrors().isEmpty());
        Assert.assertNotNull(packages.getErrors().get("rawMaterialId"));
    }

    @Test
    public void isValidQuantityTest() {
        Packages packages = new Packages();
        packages.setQuantity(null);

        boolean isValidQuantity = packages.isValidPackage();

        Assert.assertFalse(isValidQuantity);
        Assert.assertFalse(packages.getErrors().isEmpty());
        Assert.assertNotNull(packages.getErrors().get("quantity"));
    }

    @Test
    public void isValidManufacturingCostTest() {
        Packages packages = new Packages();
        packages.setManufacturingCost(null);

        boolean isValidManufacturingCost = packages.isValidPackage();

        Assert.assertFalse(isValidManufacturingCost);
        Assert.assertFalse(packages.getErrors().isEmpty());
        Assert.assertNotNull(packages.getErrors().get("manufacturingCost"));
    }

    @Test
    public void isValidWholeSaleTest() {
        Packages packages = new Packages();
        packages.setWholesaleCost(null);

        boolean isValidWholesaleCost = packages.isValidPackage();

        Assert.assertFalse(isValidWholesaleCost);
        Assert.assertFalse(packages.getErrors().isEmpty());
        Assert.assertNotNull(packages.getErrors().get("wholesaleCost"));
    }

    @Test
    public void isValidRetailCostTest() {
        Packages packages = new Packages();
        packages.setWholesaleCost(null);

        boolean isValidRetailCost = packages.isValidPackage();

        Assert.assertFalse(isValidRetailCost);
        Assert.assertFalse(packages.getErrors().isEmpty());
        Assert.assertNotNull(packages.getErrors().get("retailCost"));
    }

}
