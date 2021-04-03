package com.food.cpg.purchaseorder;

import java.util.List;

import com.food.cpg.authentication.AuthenticationSessionDetails;
import com.food.cpg.databasepersistence.PersistenceFactory;
import com.food.cpg.rawmaterial.RawMaterial;

public class PurchaseOrderByItem {

    private Integer itemId;
    private Double itemQuantity;

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Double getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(Double itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    private IPurchaseOrderRawMaterialPersistence getPersistence() {
        PersistenceFactory persistenceFactory = PersistenceFactory.getPersistenceFactory();
        return persistenceFactory.getPurchaseOrderRawMaterialPersistence();
    }

    public List<PurchaseOrderRawMaterial> getPurchaseOrderItemRawMaterial(int itemId) {
        return getPersistence().getPurchaseOrderItemRawMaterial(itemId);
    }

    private int getLoggedInManufacturerId() {
        AuthenticationSessionDetails authenticationSessionDetails = AuthenticationSessionDetails.getInstance();
        return authenticationSessionDetails.getAuthenticatedUserId();
    }

    public void createPurchaseOrderByItem(RawMaterial rawMaterial) {
        PurchaseOrder purchaseOrder = new PurchaseOrder();

        List<PurchaseOrderRawMaterial> purchaseOrderItemRawMaterials = getPurchaseOrderItemRawMaterial(this.getItemId());
        for (PurchaseOrderRawMaterial purchaseOrderRawMaterial : purchaseOrderItemRawMaterials) {
            double requiredQuantityPerItem = purchaseOrderRawMaterial.getRawMaterialQuantity();
            purchaseOrderRawMaterial.setRawMaterialQuantity(getItemQuantity() * requiredQuantityPerItem);
            purchaseOrderRawMaterial.loadCost(rawMaterial);
            purchaseOrder.addPurchaseOrderRawMaterials(purchaseOrderRawMaterial);
        }

        purchaseOrder.setManufacturerId(this.getLoggedInManufacturerId());
        purchaseOrder.save();
    }
}
