package com.food.cpg.purchaseorder;

import com.food.cpg.databasepersistence.PersistenceFactory;
import com.food.cpg.rawmaterial.RawMaterial;

public class PurchaseOrderRawMaterial {
    private String purchaseOrderNumber;
    private int rawMaterialId;
    private double rawMaterialCost;
    private double rawMaterialQuantity;
    private String rawMaterialQuantityUOM;

    public String getPurchaseOrderNumber() {
        return purchaseOrderNumber;
    }

    public void setPurchaseOrderNumber(String purchaseOrderNumber) {
        this.purchaseOrderNumber = purchaseOrderNumber;
    }

    public int getRawMaterialId() {
        return rawMaterialId;
    }

    public void setRawMaterialId(int rawMaterialId) {
        this.rawMaterialId = rawMaterialId;
    }

    public double getRawMaterialCost() {
        return rawMaterialCost;
    }

    public void setRawMaterialCost(double rawMaterialCost) {
        this.rawMaterialCost = rawMaterialCost;
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

    public void save() {
        getPersistence().save(this);
    }

    public void loadCost(RawMaterial rawMaterial) {
        double cost = rawMaterial.getCost(this.getRawMaterialId());
        this.setRawMaterialCost(cost * getRawMaterialQuantity());
    }

    public void delete() {
        getPersistence().delete(this.getPurchaseOrderNumber());
    }

    private IPurchaseOrderRawMaterialPersistence getPersistence() {
        PersistenceFactory persistenceFactory = PersistenceFactory.getPersistenceFactory();
        return persistenceFactory.getPurchaseOrderRawMaterialPersistence();
    }
}
