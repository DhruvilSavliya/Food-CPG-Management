package com.food.cpg.packaging;

public interface IPackage {
    public Integer getPackageId();

    public void setPackageId(Integer packageId);

    public Integer getItemId();
    public void setItemId(Integer itemId);

    public String getPackageName();
    public void setPackageName(String packageName);

    public Double getQuantity();

    public void setQuantity(Double quantity);

    public Double getManufacturingCost();

    public void setManufacturingCost(Double manufacturingCost);

    public Double getWholesaleCost();

    public void setWholesaleCost(Double wholesaleCost);

    public Double getRetailCost();

    public void setRetailCost(Double retailCost);

    public Integer getManufacturerId();

    public void setManufacturerId(Integer manufacturerId);

    public void load();
}
