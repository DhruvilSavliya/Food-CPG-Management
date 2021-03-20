package com.food.cpg.vendor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.food.cpg.authentication.AuthenticationSessionDetails;
import com.food.cpg.databasepersistence.PersistenceFactory;

public class Vendor {

    private Integer id;
    private Integer manufacturerId;
    private String name;
    private String address;
    private String contactPersonName;
    private String contactPersonEmail;
    private Long contactPersonPhone;

    private Map<String, String> errors = new HashMap<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(Integer manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactPersonName() {
        return contactPersonName;
    }

    public void setContactPersonName(String contactPersonName) {
        this.contactPersonName = contactPersonName;
    }

    public String getContactPersonEmail() {
        return contactPersonEmail;
    }

    public void setContactPersonEmail(String contactPersonEmail) {
        this.contactPersonEmail = contactPersonEmail;
    }

    public Long getContactPersonPhone() {
        return contactPersonPhone;
    }

    public void setContactPersonPhone(Long contactPersonPhone) {
        this.contactPersonPhone = contactPersonPhone;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    public boolean isValidVendor() {
        errors = new HashMap<>();

        boolean isValid = true;

        if (StringUtils.isEmpty(getName())) {
            errors.put("name", "Vendor name is required.");
            isValid = false;
        }

        if (StringUtils.isEmpty(getContactPersonName())) {
            errors.put("contactPersonName", "Contact person is required.");
            isValid = false;
        }

        if (StringUtils.isEmpty(getContactPersonEmail())) {
            errors.put("contactPersonEmail", "Email is required.");
            isValid = false;
        }

        if (getContactPersonPhone() == null) {
            errors.put("contactPersonPhone", "Phone number is required.");
            isValid = false;
        }

        if (getContactPersonPhone() != null && String.valueOf(getContactPersonPhone()).length() != 10) {
            errors.put("contactPersonPhone", "Invalid phone number.");
            isValid = false;
        }

        return isValid;
    }

    public List<Vendor> getAll() {
        int loggedInManufacturerId = getLoggedInManufacturerId();
        return getPersistence().getAll(loggedInManufacturerId);
    }

    public void save() {
        int loggedInManufacturerId = getLoggedInManufacturerId();
        this.setManufacturerId(loggedInManufacturerId);

        getPersistence().save(this);
    }

    public void load() {
        if (this.getId() > 0) {
            getPersistence().load(this);
        }
    }

    public void update() {
        getPersistence().update(this);
    }

    public void delete() {
        getPersistence().delete(this.getId());
    }

    private IVendorPersistence getPersistence() {
        PersistenceFactory persistenceFactory = PersistenceFactory.getPersistenceFactory();
        return persistenceFactory.getVendorPersistence();
    }

    private int getLoggedInManufacturerId() {
        AuthenticationSessionDetails authenticationSessionDetails = AuthenticationSessionDetails.getInstance();
        return authenticationSessionDetails.getAuthenticatedUserId();
    }
}
