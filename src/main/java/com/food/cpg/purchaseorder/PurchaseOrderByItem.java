package com.food.cpg.purchaseorder;

import com.food.cpg.databasepersistence.PersistenceFactory;

import java.util.ArrayList;
import java.util.List;

public class PurchaseOrderByItem {

    private String orderNumber;
    private Integer itemId;
    private Double itemQuantity;
    private List<PurchaseOrderRawMaterial> purchaseOrderRawMaterials;
    private PurchaseOrder purchaseOrder;

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

    public List<PurchaseOrderRawMaterial> getPurchaseOrderRawMaterials() {
        return purchaseOrderRawMaterials;
    }

    public void setPurchaseOrderRawMaterials(List<PurchaseOrderRawMaterial> purchaseOrderRawMaterials) {
        this.purchaseOrderRawMaterials = purchaseOrderRawMaterials;
    }

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    private IPurchaseOrderRawMaterialPersistence getPersistence() {
        PersistenceFactory persistenceFactory = PersistenceFactory.getPersistenceFactory();
        return persistenceFactory.getPurchaseOrderRawMaterialPersistence();
    }

    public List<PurchaseOrderRawMaterial> getPurchaseOrderItemRawMaterial(int itemId) {
        return getPersistence().getPurchaseOrderItemRawMaterial(itemId);
    }

    public void addPurchaseOrderByItemRawMaterials() {
        if (this.purchaseOrderRawMaterials == null) {
            this.purchaseOrderRawMaterials = new ArrayList<>();
        }
        List<PurchaseOrderRawMaterial> purchaseOrderItemRawMaterials = getPurchaseOrderItemRawMaterial(this.getItemId());
        for (PurchaseOrderRawMaterial purchaseOrderRawMaterial : purchaseOrderItemRawMaterials) {
            purchaseOrderRawMaterial.setPurchaseOrderNumber(this.getOrderNumber());
            Double rawMaterialQuantity = purchaseOrderRawMaterial.getRawMaterialQuantity();
            Double totalQuantity = itemQuantity * rawMaterialQuantity;
            purchaseOrderRawMaterial.setRawMaterialQuantity(totalQuantity);
            this.purchaseOrderRawMaterials.add(purchaseOrderRawMaterial);
        }
    }


}
