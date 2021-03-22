package com.food.cpg.vendor;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.food.cpg.databasepersistence.ICommonDatabaseOperation;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Vendor.class)
public class VendorTest {

    @Mock
    IVendorPersistence vendorPersistence;

    @Mock
    ICommonDatabaseOperation commonDatabaseOperation;

    @Test
    public void isValidVendorNameTest() {
        Vendor vendor = new Vendor();
        vendor.setName("");

        boolean isValidName = vendor.isValidVendor();

        Assert.assertFalse(isValidName);
        Assert.assertFalse(vendor.getErrors().isEmpty());
        Assert.assertNotNull(vendor.getErrors().get("name"));
    }

    @Test
    public void isValidVendorContactPersonNameTest() {
        Vendor vendor = new Vendor();
        vendor.setContactPersonName("");

        boolean isValidContact = vendor.isValidVendor();

        Assert.assertFalse(isValidContact);
        Assert.assertFalse(vendor.getErrors().isEmpty());
        Assert.assertNotNull(vendor.getErrors().get("contactPersonName"));
    }

    @Test
    public void isValidVendorContactPersonEmailTest() {
        Vendor vendor = new Vendor();
        vendor.setContactPersonEmail("");

        boolean isValidContactEmail = vendor.isValidVendor();

        Assert.assertFalse(isValidContactEmail);
        Assert.assertFalse(vendor.getErrors().isEmpty());
        Assert.assertNotNull(vendor.getErrors().get("contactPersonEmail"));
    }

    @Test
    public void isValidVendorContactPersonPhoneTest() {
        Vendor vendor = new Vendor();

        boolean isValidContactPhone = vendor.isValidVendor();

        Assert.assertFalse(isValidContactPhone);
        Assert.assertFalse(vendor.getErrors().isEmpty());
        Assert.assertNotNull(vendor.getErrors().get("contactPersonPhone"));
    }

    @Test
    public void isValidVendorContactPersonPhoneDigitTest() {
        Vendor vendor = new Vendor();
        vendor.setContactPersonPhone(987656743L);

        boolean isValidContactPhone = vendor.isValidVendor();

        Assert.assertFalse(isValidContactPhone);
        Assert.assertFalse(vendor.getErrors().isEmpty());
        Assert.assertNotNull(vendor.getErrors().get("contactPersonPhone"));
    }

    @Test
    public void allVendorPropertiesValidTest() {
        Vendor vendor = new Vendor();
        vendor.setName("Test vendor 1");
        vendor.setContactPersonName("Kartik");
        vendor.setContactPersonEmail("kartik@testvendor.com");
        vendor.setContactPersonPhone(9876567432L);

        boolean isValidVendor = vendor.isValidVendor();

        Assert.assertTrue(isValidVendor);
        Assert.assertTrue(vendor.getErrors().isEmpty());
    }

    @Before
    public void setUp() {
        vendorPersistence = spy(new VendorDatabasePersistence(commonDatabaseOperation));
    }

    @Test
    public void getAllTest() throws Exception {
        Vendor vendor = spy(new Vendor());
        vendor.setManufacturerId(1);
        vendor.setName("Test Vendor 1");

        List<Vendor> vendors = new ArrayList<>();
        vendors.add(vendor);

        PowerMockito.doReturn(vendorPersistence).when(vendor, "getPersistence");
        PowerMockito.doReturn(vendors).when(vendorPersistence).getAll(anyInt());

        List<Vendor> vendorsResult = vendor.getAll();
        Assert.assertNotNull(vendorsResult);
        Assert.assertEquals(1, vendorsResult.size());
        Assert.assertEquals("Test Vendor 1", vendorsResult.get(0).getName());
    }

    @Test
    public void saveTest() throws Exception {
        Vendor vendor = spy(new Vendor());
        vendor.setManufacturerId(1);
        vendor.setName("Test Vendor 1");

        PowerMockito.doReturn(vendorPersistence).when(vendor, "getPersistence");
        PowerMockito.doNothing().when(vendorPersistence).save(vendor);
        PowerMockito.doReturn(1).when(vendor, "getLoggedInManufacturerId");

        vendor.save();
        verifyPrivate(vendor).invoke("getPersistence");
        verify(vendorPersistence, times(1)).save(vendor);
    }

    @Test
    public void loadSuccessTest() throws Exception {
        Vendor vendor = spy(new Vendor());
        vendor.setId(1);
        vendor.setManufacturerId(1);
        vendor.setName("Test Vendor 1");

        PowerMockito.doReturn(vendorPersistence).when(vendor, "getPersistence");
        PowerMockito.doNothing().when(vendorPersistence).load(vendor);

        vendor.load();
        verifyPrivate(vendor).invoke("getPersistence");
        verify(vendorPersistence, times(1)).load(vendor);
    }

    @Test
    public void loadFailTest() throws Exception {
        Vendor vendor = spy(new Vendor());
        vendor.setId(0);
        vendor.setManufacturerId(1);
        vendor.setName("Test Vendor 1");

        PowerMockito.doReturn(vendorPersistence).when(vendor, "getPersistence");
        PowerMockito.doNothing().when(vendorPersistence).load(vendor);

        vendor.load();
        verify(vendorPersistence, times(0)).load(vendor);
    }

    @Test
    public void deleteTest() throws Exception {
        Vendor vendor = spy(new Vendor());
        vendor.setId(1);
        vendor.setManufacturerId(1);
        vendor.setName("Test Vendor 1");

        PowerMockito.doReturn(vendorPersistence).when(vendor, "getPersistence");
        PowerMockito.doNothing().when(vendorPersistence).delete(anyInt());

        vendor.delete();
        verifyPrivate(vendor).invoke("getPersistence");
        verify(vendorPersistence, times(1)).delete(vendor.getId());
    }
}
