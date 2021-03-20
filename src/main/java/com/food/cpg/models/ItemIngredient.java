package com.food.cpg.models;


public class ItemIngredient {
    private Integer itemId;
    private Integer rawMaterialId;
    private Integer vendorId;
    private double rawMaterialQuantity;
    private String rawMaterialQuantityUOM;

//    public ItemIngredient(int rawMaterialId, double rawMaterialQuantity, String rawMaterialMeasurementUOM) {
//        this.rawMaterialId = rawMaterialId;
//        this.rawMaterialQuantity = rawMaterialQuantity;
//        this.rawMaterialMeasurementUOM = rawMaterialMeasurementUOM;
//    }


    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getRawMaterialId() {
        return rawMaterialId;
    }

    public void setRawMaterialId(Integer rawMaterialId) {
        this.rawMaterialId = rawMaterialId;
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public double getRawMaterialQuantity() {
        return rawMaterialQuantity;
    }

    public void setRawMaterialQuantity(double rawMaterialQuantity) {
        this.rawMaterialQuantity = rawMaterialQuantity;
    }

    public String getRawMaterialQuantityUOM() {
        return rawMaterialQuantityUOM;
    }

    public void setRawMaterialQuantityUOM(String rawMaterialQuantityUOM) {
        this.rawMaterialQuantityUOM = rawMaterialQuantityUOM;
    }
}
