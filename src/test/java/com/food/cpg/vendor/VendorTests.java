package com.food.cpg.vendor;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * @author Kartik Gevariya
 */
@RunWith(MockitoJUnitRunner.class)
public class VendorTests {

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
}
