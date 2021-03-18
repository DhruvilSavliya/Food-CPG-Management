package com.food.cpg.models;


public class ItemIngredient {
    private int itemId;
    private int rawMaterialId;
    private double rawMaterialQuantity;
    private String rawMaterialMeasurementUOM;

//    public ItemIngredient(int rawMaterialId, double rawMaterialQuantity, String rawMaterialMeasurementUOM) {
//        this.rawMaterialId = rawMaterialId;
//        this.rawMaterialQuantity = rawMaterialQuantity;
//        this.rawMaterialMeasurementUOM = rawMaterialMeasurementUOM;
//    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getRawMaterialId() {
        return rawMaterialId;
    }

    public void setRawMaterialId(int rawMaterialId) {
        this.rawMaterialId = rawMaterialId;
    }

    public double getRawMaterialQuantity() {
        return rawMaterialQuantity;
    }

    public void setRawMaterialQuantity(double rawMaterialQuantity) {
        this.rawMaterialQuantity = rawMaterialQuantity;
    }

    public String getRawMaterialMeasurementUOM() {
        return rawMaterialMeasurementUOM;
    }

    public void setRawMaterialMeasurementUOM(String rawMaterialMeasurementUOM) {
        this.rawMaterialMeasurementUOM = rawMaterialMeasurementUOM;
    }
}
