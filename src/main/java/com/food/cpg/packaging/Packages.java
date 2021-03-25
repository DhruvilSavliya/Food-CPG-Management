package com.food.cpg.packaging;

import com.food.cpg.authentication.AuthenticationSessionDetails;
import com.food.cpg.databasepersistence.PersistenceFactory;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Packages {
    private Integer packageId;
    private Integer itemId;
    private Integer manufacturerId;
    private String packageName;
    private Double quantity;
    private Double manufacturingCost;
    private Double wholesaleCost;
    private Double retailCost;


    private Map<String, String> errors = new HashMap<>();

    public Integer getPackageId() {
        return packageId;
    }

    public void setPackageId(Integer packageId) {
        this.packageId = packageId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getManufacturingCost() {
        return manufacturingCost;
    }

    public void setManufacturingCost(Double manufacturingCost) {
        this.manufacturingCost = manufacturingCost;
    }

    public Double getWholesaleCost() {
        return wholesaleCost;
    }

    public void setWholesaleCost(Double wholesaleCost) {
        this.wholesaleCost = wholesaleCost;
    }

    public Double getRetailCost() {
        return retailCost;
    }

    public void setRetailCost(Double retailCost) {
        this.retailCost = retailCost;
    }

    public Integer getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(Integer manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    public boolean isValidPackage() {
        errors = new HashMap<>();

        boolean isValid = true;

        if (StringUtils.isEmpty(this.getPackageName())) {
            errors.put("packageName", "Package name is required.");
            isValid = false;
        }

        if (this.getItemId() == null) {
            errors.put("item", "Valid item is required.");
            isValid = false;
        }

        if (this.getQuantity() == null) {
            errors.put("quantity", "Quantity is required.");
            isValid = false;
        }

        if (this.getManufacturingCost() == null) {
            errors.put("manufacturingCost", "Manufacturing cost is required.");
            isValid = false;
        }

        if (this.getWholesaleCost() == null) {
            errors.put("wholesaleCost", "Wholesale cost is required.");
            isValid = false;
        }

        if (this.getRetailCost() == null) {
            errors.put("retailCost", "Retail cost is required.");
            isValid = false;
        }

        return isValid;
    }

    public List<Packages> getAll() {
        int loggedInManufacturerId = getLoggedInManufacturerId();
        return getPersistence().getAll(loggedInManufacturerId);
    }

    public void save() {
        int loggedInManufacturerId = getLoggedInManufacturerId();
        this.setManufacturerId(loggedInManufacturerId);

        getPersistence().save(this);
    }

    public void load() {
        if (this.getPackageId() > 0) {
            getPersistence().load(this);
        }
    }

    public void update() {
        getPersistence().update(this);
    }

    public void delete() {
        getPersistence().delete(this.getPackageId());
    }

    private IPackagesPersistence getPersistence() {
        PersistenceFactory persistenceFactory = PersistenceFactory.getPersistenceFactory();
        return persistenceFactory.getPackagesPersistence();
    }

    private int getLoggedInManufacturerId() {
        AuthenticationSessionDetails authenticationSessionDetails = AuthenticationSessionDetails.getInstance();
        return authenticationSessionDetails.getAuthenticatedUserId();
    }

}
