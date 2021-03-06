package com.food.cpg.models;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

/**
 * @author Dhruvilkumar Savliya
 */
public class Packages {
    private Integer packageId;
    private Integer itemId;
    private String packageName;
    private Integer quantity;
    private Integer manufacturingCost;
    private Integer wholesaleCost;
    private Integer retailCost;
    private RawMaterial rawMaterial;
    private Integer rawMaterialId;

    private Map<String, String> errors = new HashMap<>();


    public Integer getPackageId()
    {
        return packageId;
    }

    public void setPackageId(Integer packageId)
    {
        this.packageId = packageId;
    }

    public Integer getItemId()
    {
        return itemId;
    }

    public void setItemId(Integer itemId)
    {
        this.itemId = itemId;
    }

    public String getPackageName()
    {
        return packageName;
    }

    public void setPackageName(String packageName)
    {
        this.packageName = packageName;
    }

    public Integer getQuantity()
    {
        return quantity;
    }

    public void setQuantity(Integer quantity)
    {
        this.quantity = quantity;
    }

    public Integer getManufacturingCost()
    {
        return manufacturingCost;
    }

    public void setManufacturingCost(Integer manufacturingCost)
    {
        this.manufacturingCost = manufacturingCost;
    }

    public Integer getWholesaleCost()
    {
        return wholesaleCost;
    }

    public void setWholesaleCost(Integer wholesaleCost)
    {
        this.wholesaleCost = wholesaleCost;
    }

    public Integer getRetailCost()
    {
        return retailCost;
    }

    public void setRetailCost(Integer retailCost)
    {
        this.retailCost = retailCost;
    }

    public RawMaterial getRawMaterial() { return rawMaterial; }

    public void setRawMaterial(RawMaterial rawMaterial)
    {
        this.rawMaterial = rawMaterial;
    }

    public Integer getRawMaterialId() { return rawMaterialId; }

    public void setRawMaterialId(Integer rawMaterialId) { this.rawMaterialId = rawMaterialId; }

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

        if (this.getRawMaterialId() == null) {
            errors.put("rawMaterialId", "Valid item is required.");
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


}