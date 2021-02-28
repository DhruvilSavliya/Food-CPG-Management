package com.food.cpg.models;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * @author Dhruvilkumar Savliya
 */
@RunWith(MockitoJUnitRunner.class)
public class AdminTests
{
    @Test
    public void isValidUserNameTest() {
        Admin admin = new Admin();
        admin.setName("");

        boolean isValidName = admin.isValidAdmin();

        Assert.assertFalse(isValidName);
        Assert.assertFalse(admin.getErrors().isEmpty());
        Assert.assertNotNull(admin.getErrors().get("Username"));
    }

    @Test
    public void isValidPasswordTest() {
        Admin admin = new Admin();
        admin.setPassword("");

        boolean isValidPassword = admin.isValidAdmin();

        Assert.assertFalse(isValidPassword);
        Assert.assertFalse(admin.getErrors().isEmpty());
        Assert.assertNotNull(admin.getErrors().get("Password"));
    }

    @Test
    public void allAdminPropertiesValidTest() {
        Admin admin= new Admin();
        admin.setName("Admin");
        admin.setPassword("dhr123");

        boolean isValidAdmin = admin.isValidAdmin();

        Assert.assertTrue(isValidAdmin);
        Assert.assertTrue(admin.getErrors().isEmpty());
    }
}
