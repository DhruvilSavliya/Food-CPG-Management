package com.food.cpg.models;

import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class Manufacturer {
    private String companyName;
    private String email;
    private String password;
    private Long contact;
    private String address;

    private Map<String, String> errors = new HashMap<>();

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public Long getContact() {
        return contact;
    }

    public void setContact(Long contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isValidManufacturer() {
        errors = new HashMap<>();

        boolean isValid = true;

        if (StringUtils.isEmpty(getCompanyName())) {
            errors.put("companyName", "Company name is required.");
            isValid = false;
        }

        if (StringUtils.isEmpty(getEmail())) {
            errors.put("email", "Email address is required.");
            isValid = false;
        }

        if (StringUtils.isEmpty(getPassword())) {
            errors.put("password", "Password can not be empty.");
            isValid = false;
        }

        if (getContact() == null) {
            errors.put("contact", "Phone number is required.");
            isValid = false;
        }

        if (getContact() != null && String.valueOf(getContact()).length() != 10) {
            errors.put("contactLength", "Invalid phone number.");
            isValid = false;
        }

        if(StringUtils.isEmpty(getAddress())){
            errors.put("address", "Address can not be empty.");
        }

        return isValid;
    }
}
