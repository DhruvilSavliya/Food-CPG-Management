package com.food.cpg.models;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

public class User {
    private Integer userId;
    private String email;
    private String password;
    private String role;
    private Boolean status;

    private Map<String, String> errors = new HashMap<>();

    public Integer getId() {
        return userId;
    }

    public void setId(Integer userid) {
        this.userId = userid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus (Boolean status) {
        this.status = status;
    }


    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    public boolean isValidUser() {
        errors = new HashMap<>();

        boolean isValid = true;

        if (StringUtils.isEmpty(getEmail())) {
            errors.put("email", "Email is required.");
            isValid = false;
        }

        if (StringUtils.isEmpty(getPassword())) {
            errors.put("password", "Password is required.");
            isValid = false;
        }
        return isValid;
    }
}
