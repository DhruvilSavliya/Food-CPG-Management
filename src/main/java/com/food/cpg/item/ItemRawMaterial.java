package com.food.cpg.item;

import com.food.cpg.databasepersistence.PersistenceFactory;
import com.food.cpg.purchaseorder.IPurchaseOrderRawMaterialPersistence;

public class ItemRawMaterial {

    private Integer itemId;
    private Integer rawMaterialId;
    private Integer vendorId;
    private Double rawMaterialQuantity;
    private String rawMaterialQuantityUOM;
    private Double rawMaterialUnitCost;
    private Double cost;

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

    public Double getRawMaterialUnitCost() {
        return rawMaterialUnitCost;
    }

    public void setRawMaterialUnitCost(Double rawMaterialUnitCost) {
        this.rawMaterialUnitCost = rawMaterialUnitCost;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public void save() {
        getPersistence().save(this);
    }

    public void delete() {
        getPersistence().delete(this.getItemId());
    }

    public void loadUnitCost(){
        Integer rawMaterialId = getRawMaterialId();
        Double unitCost = getPersistence().loadUnitCost(rawMaterialId);
        setRawMaterialUnitCost(unitCost);
    }

    public Double calculateCost(){
        loadUnitCost();
        Double totalCost = getRawMaterialQuantity() * getRawMaterialUnitCost();
        return totalCost;
    }

    private IItemRawMaterialPersistence getPersistence() {
        PersistenceFactory persistenceFactory = PersistenceFactory.getPersistenceFactory();
        return persistenceFactory.getItemRawMaterialPersistence();
    }
}
