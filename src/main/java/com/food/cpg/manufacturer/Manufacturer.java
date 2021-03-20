package com.food.cpg.manufacturer;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;

import com.food.cpg.databasepersistence.PersistenceFactory;

public class Manufacturer {
    private Integer id;
    private String companyName;
    private String email;
    private String password;
    private Long contact;
    private String address;

    private Map<String, String> errors = new HashMap<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
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

        if (StringUtils.isEmpty(getAddress())) {
            errors.put("address", "Address can not be empty.");
        }

        return isValid;
    }

    public void register() {
        getPersistence().register(this);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(this.getPassword());
        this.setPassword(encodedPassword);

        getPersistence().createLoginAccount(this);
    }

    public void load() {
        getPersistence().load(this);
    }

    private IManufacturerPersistence getPersistence() {
        PersistenceFactory persistenceFactory = PersistenceFactory.getPersistenceFactory();
        return persistenceFactory.getManufacturerPersistence();
    }
}
