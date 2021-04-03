package com.food.cpg.purchaseorder;

import com.food.cpg.databasepersistence.PersistenceFactory;
import com.food.cpg.rawmaterial.RawMaterial;

public class PurchaseOrderRawMaterial {
    private String purchaseOrderNumber;
    private Integer rawMaterialId;
    private Double rawMaterialCost;
    private Double rawMaterialQuantity;
    private String rawMaterialQuantityUOM;

    public String getPurchaseOrderNumber() {
        return purchaseOrderNumber;
    }

    public void setPurchaseOrderNumber(String purchaseOrderNumber) {
        this.purchaseOrderNumber = purchaseOrderNumber;
    }

    public Integer getRawMaterialId() {
        return rawMaterialId;
    }

    public void setRawMaterialId(Integer rawMaterialId) {
        this.rawMaterialId = rawMaterialId;
    }

    public Double getRawMaterialCost() {
        return rawMaterialCost;
    }

    public void setRawMaterialCost(Double rawMaterialCost) {
        this.rawMaterialCost = rawMaterialCost;
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
