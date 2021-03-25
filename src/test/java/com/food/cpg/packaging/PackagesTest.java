package com.food.cpg.packaging;

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
@PrepareForTest(Packages.class)

public class PackagesTest {
    public static final String GET_PERSISTENCE_METHOD = "getPersistence";
    public static final String GET_LOGGED_IN_MANUFACTURER_ID_METHOD = "getLoggedInManufacturerId";
    private static final String EMPTY_STRING = "";
    private static final String PACKAGE_NAME_ATTRIBUTE = "packageName";
    private static final String ITEM_ATTRIBUTE = "item";
    private static final String QUANTITY_ATTRIBUTE = "quantity";
    private static final String MANUFACTURING_COST_ATTRIBUTE = "manufacturingCost";
    private static final String WHOLE_SALE_COST_ATTRIBUTE = "wholesaleCost";
    private static final String RETAIL_COST_ATTRIBUTE = "retailCost";
    private static final Integer TEST_MANUFACTURER_ID = 1;
    private static final Integer TEST_PACKAGE_ID = 1;
    private static final String TEST_PACKAGE_NAME = "Test package 1";
    @Mock
    IPackagesPersistence packagesPersistence;

    @Test
    public void isValidPackageNameTest() {
        Packages packages = new Packages();
        packages.setPackageName(EMPTY_STRING);

        boolean isValidName = packages.isValidPackage();

        Assert.assertFalse(isValidName);
        Assert.assertFalse(packages.getErrors().isEmpty());
        Assert.assertNotNull(packages.getErrors().get(PACKAGE_NAME_ATTRIBUTE));
    }

    @Test
    public void isValidPackageItemTest() {
        Packages packages = new Packages();

        boolean isValidItem = packages.isValidPackage();

        Assert.assertFalse(isValidItem);
        Assert.assertFalse(packages.getErrors().isEmpty());
        Assert.assertNotNull(packages.getErrors().get(ITEM_ATTRIBUTE));
    }

    @Test
    public void isValidQuantityTest() {
        Packages packages = new Packages();

        boolean isValidQuantity = packages.isValidPackage();

        Assert.assertFalse(isValidQuantity);
        Assert.assertFalse(packages.getErrors().isEmpty());
        Assert.assertNotNull(packages.getErrors().get(QUANTITY_ATTRIBUTE));
    }

    @Test
    public void isValidManufacturingCostTest() {
        Packages packages = new Packages();

        boolean isValidManufacturingCost = packages.isValidPackage();

        Assert.assertFalse(isValidManufacturingCost);
        Assert.assertFalse(packages.getErrors().isEmpty());
        Assert.assertNotNull(packages.getErrors().get(MANUFACTURING_COST_ATTRIBUTE));
    }

    @Test
    public void isValidWholeSaleTest() {
        Packages packages = new Packages();

        boolean isValidWholesaleCost = packages.isValidPackage();

        Assert.assertFalse(isValidWholesaleCost);
        Assert.assertFalse(packages.getErrors().isEmpty());
        Assert.assertNotNull(packages.getErrors().get(WHOLE_SALE_COST_ATTRIBUTE));
    }

    @Test
    public void isValidRetailCostTest() {
        Packages packages = new Packages();

        boolean isValidRetailCost = packages.isValidPackage();

        Assert.assertFalse(isValidRetailCost);
        Assert.assertFalse(packages.getErrors().isEmpty());
        Assert.assertNotNull(packages.getErrors().get(RETAIL_COST_ATTRIBUTE));
    }

    @Test
    public void getAllTest() throws Exception {
        Packages packages = spy(new Packages());
        packages.setManufacturerId(TEST_MANUFACTURER_ID);
        packages.setPackageName(TEST_PACKAGE_NAME);

        List<Packages> packagesList = new ArrayList<>();
        packagesList.add(packages);

        PowerMockito.doReturn(packagesPersistence).when(packages, GET_PERSISTENCE_METHOD);
        PowerMockito.doReturn(packagesList).when(packagesPersistence).getAll(anyInt());

        List<Packages> packagesResult = packages.getAll();
        Assert.assertNotNull(packagesResult);
        Assert.assertEquals(1, packagesResult.size());
        Assert.assertEquals(TEST_PACKAGE_NAME, packagesResult.get(0).getPackageName());
    }

    @Test
    public void saveTest() throws Exception {
        Packages packages = spy(new Packages());
        packages.setManufacturerId(TEST_MANUFACTURER_ID);
        packages.setPackageName(TEST_PACKAGE_NAME);

        PowerMockito.doReturn(packagesPersistence).when(packages, GET_PERSISTENCE_METHOD);
        PowerMockito.doNothing().when(packagesPersistence).save(packages);
        PowerMockito.doReturn(1).when(packages, GET_LOGGED_IN_MANUFACTURER_ID_METHOD);

        packages.save();
        verifyPrivate(packages).invoke(GET_PERSISTENCE_METHOD);
        verify(packagesPersistence, times(1)).save(packages);
    }

    @Test
    public void loadSuccessTest() throws Exception {
        Packages packages = spy(new Packages());
        packages.setPackageId(TEST_PACKAGE_ID);
        packages.setManufacturerId(TEST_MANUFACTURER_ID);
        packages.setPackageName(TEST_PACKAGE_NAME);

        PowerMockito.doReturn(packagesPersistence).when(packages, GET_PERSISTENCE_METHOD);
        PowerMockito.doNothing().when(packagesPersistence).load(packages);

        packages.load();
        verifyPrivate(packages).invoke(GET_PERSISTENCE_METHOD);
        verify(packagesPersistence, times(1)).load(packages);
    }

    @Test
    public void loadFailTest() throws Exception {
        Packages packages = spy(new Packages());
        packages.setPackageId(0);
        packages.setManufacturerId(TEST_MANUFACTURER_ID);
        packages.setPackageName(TEST_PACKAGE_NAME);

        PowerMockito.doReturn(packagesPersistence).when(packages, GET_PERSISTENCE_METHOD);
        PowerMockito.doNothing().when(packagesPersistence).load(packages);

        packages.load();
        verify(packagesPersistence, times(0)).load(packages);
    }

    @Test
    public void updateTest() throws Exception {
        Packages packages = spy(new Packages());
        packages.setManufacturerId(TEST_MANUFACTURER_ID);
        packages.setPackageName(TEST_PACKAGE_NAME);

        PowerMockito.doReturn(packagesPersistence).when(packages, GET_PERSISTENCE_METHOD);
        PowerMockito.doNothing().when(packagesPersistence).update(packages);

        packages.update();
        verifyPrivate(packages).invoke(GET_PERSISTENCE_METHOD);
        verify(packagesPersistence, times(1)).update(packages);
    }

    @Test
    public void deleteTest() throws Exception {
        Packages packages = spy(new Packages());
        packages.setPackageId(TEST_PACKAGE_ID);
        packages.setManufacturerId(TEST_MANUFACTURER_ID);
        packages.setPackageName(TEST_PACKAGE_NAME);

        PowerMockito.doReturn(packagesPersistence).when(packages, GET_PERSISTENCE_METHOD);
        PowerMockito.doNothing().when(packagesPersistence).delete(anyInt());

        packages.delete();
        verifyPrivate(packages).invoke(GET_PERSISTENCE_METHOD);
        verify(packagesPersistence, times(1)).delete(packages.getPackageId());
    }
}