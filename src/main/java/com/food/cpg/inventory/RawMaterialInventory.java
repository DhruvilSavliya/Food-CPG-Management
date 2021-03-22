package com.food.cpg.inventory;

public class RawMaterialInventory {

    private Integer rawMaterialId;
    private String rawMaterialName;
    private String vendorName;
    private Double rawMaterialQuantity;
    private String rawMaterialQuantityUOM;
    private Integer manufacturerId;

    public Integer getRawMaterialId() {
        return rawMaterialId;
    }

    public void setRawMaterialId(Integer rawMaterialId) {
        this.rawMaterialId = rawMaterialId;
    }

    public String getRawMaterialName() {
        return rawMaterialName;
    }

    public void setRawMaterialName(String rawMaterialName) {
        this.rawMaterialName = rawMaterialName;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public Double getRawMaterialQuantity() {
        return rawMaterialQuantity;
    }

    public void setRawMaterialQuantity(Double rawMaterialQuantity) {
        this.rawMaterialQuantity = rawMaterialQuantity;
    }

    public String getRawMaterialQuantityUOM() {
        return rawMaterialQuantityUOM;
    }

    public void setRawMaterialQuantityUOM(String rawMaterialQuantityUOM) {
        this.rawMaterialQuantityUOM = rawMaterialQuantityUOM;
    }

    public Integer getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(Integer manufacturerId) {
        this.manufacturerId = manufacturerId;
    }
}
