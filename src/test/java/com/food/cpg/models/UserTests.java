package com.food.cpg.models;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserTests {

    @Test
    public void isValidEmailTest() {
        User user = new User();
        user.setEmail("");

        boolean isValidEmail = user.isValidUser();

        Assert.assertFalse(isValidEmail);
        Assert.assertFalse(user.getErrors().isEmpty());
        Assert.assertNotNull(user.getErrors().get("email"));
    }

    @Test
    public void isValidPasswordTest() {
        User user = new User();
        user.setPassword("");

        boolean isValidPassword = user.isValidUser();

        Assert.assertFalse(isValidPassword);
        Assert.assertFalse(user.getErrors().isEmpty());
        Assert.assertNotNull(user.getErrors().get("password"));
    }

    @Test
    public void allUserPropertiesValidTest() {
        User user = new User();
        user.setEmail("abc@gmail.com");
        user.setPassword("abc123");

        boolean isValidUser = user.isValidUser();

        Assert.assertTrue(isValidUser);
        Assert.assertTrue(user.getErrors().isEmpty());
    }
}
