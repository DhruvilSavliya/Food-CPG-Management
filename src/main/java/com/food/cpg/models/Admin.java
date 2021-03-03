package com.food.cpg.models;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

/**
 * @author Dhruvilkumar Savliya
 */
public class Admin
{
    private Integer id;
    private String username;
    private String password;

    private Map<String, String> errors = new HashMap<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setName(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    public boolean isValidAdmin()
    {
        errors = new HashMap<>();

        boolean isValid = true;

        if (StringUtils.isEmpty(getUsername())) {
            errors.put("Username", "Username is required.");
            isValid = false;
        }

        if (StringUtils.isEmpty(getPassword())) {
            errors.put("Password", "Password is required.");
            isValid = false;
        }

        return isValid;
    }

}
