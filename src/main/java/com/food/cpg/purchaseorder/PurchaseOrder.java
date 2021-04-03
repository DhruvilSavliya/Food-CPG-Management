package com.food.cpg.purchaseorder;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.food.cpg.authentication.AuthenticationSessionDetails;
import com.food.cpg.databasepersistence.PersistenceFactory;

public class PurchaseOrder {
    private static final String PO_ORDER_TIME_FORMAT = "ddMMHHmm";
    private static final String PO_PREFIX = "PO-";

    private String orderNumber;
    private Integer manufacturerId;
    private Integer vendorId;
    private String orderStatus;
    private Timestamp orderCreationDate;
    private Timestamp orderPlacedDate;
    private Timestamp orderReceivedDate;
    private Double totalCost;
    private List<PurchaseOrderRawMaterial> purchaseOrderRawMaterials;

    public PurchaseOrder() {
        String generatedOrderNumber = generateOrderNumber();
        setOrderNumber(generatedOrderNumber);
        setTotalCost(0.0);
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Integer getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(Integer manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Timestamp getOrderCreationDate() {
        return orderCreationDate;
    }

    public void setOrderCreationDate(Timestamp orderCreationDate) {
        this.orderCreationDate = orderCreationDate;
    }

    public Timestamp getOrderPlacedDate() {
        return orderPlacedDate;
    }

    public void setOrderPlacedDate(Timestamp orderPlacedDate) {
        this.orderPlacedDate = orderPlacedDate;
    }

    public Timestamp getOrderReceivedDate() {
        return orderReceivedDate;
    }

    public void setOrderReceivedDate(Timestamp orderReceivedDate) {
        this.orderReceivedDate = orderReceivedDate;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public List<PurchaseOrderRawMaterial> getPurchaseOrderRawMaterials() {
        return purchaseOrderRawMaterials;
    }

    public void setPurchaseOrderRawMaterials(List<PurchaseOrderRawMaterial> purchaseOrderRawMaterials) {
        this.purchaseOrderRawMaterials = purchaseOrderRawMaterials;
    }

    public void addPurchaseOrderRawMaterials(PurchaseOrderRawMaterial purchaseOrderRawMaterial) {
        if (this.purchaseOrderRawMaterials == null) {
            this.purchaseOrderRawMaterials = new ArrayList<>();
        }
        purchaseOrderRawMaterial.setPurchaseOrderNumber(this.getOrderNumber());
        this.purchaseOrderRawMaterials.add(purchaseOrderRawMaterial);
        this.calculateTotalCost();
    }

    private String generateOrderNumber() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(PO_ORDER_TIME_FORMAT);
        LocalDateTime currentDateTime = LocalDateTime.now();
        String formattedCurrentDateTime = dateTimeFormatter.format(currentDateTime);

        return PO_PREFIX + formattedCurrentDateTime;
    }

    public void save() {
        int loggedInManufacturerId = getLoggedInManufacturerId();
        this.setManufacturerId(loggedInManufacturerId);

        getPersistence().save(this);
        for (PurchaseOrderRawMaterial purchaseOrderRawMaterial : getPurchaseOrderRawMaterials()) {
            purchaseOrderRawMaterial.save();
        }
    }

    public void calculateTotalCost() {
        double poRawMaterialCost = 0.0;

        for (PurchaseOrderRawMaterial purchaseOrderRawMaterial : getPurchaseOrderRawMaterials()) {
            poRawMaterialCost += purchaseOrderRawMaterial.getRawMaterialCost();
        }

        this.setTotalCost(poRawMaterialCost);
    }

    private IPurchaseOrderPersistence getPersistence() {
        PersistenceFactory persistenceFactory = PersistenceFactory.getPersistenceFactory();
        return persistenceFactory.getPurchaseOrderPersistence();
    }

    private int getLoggedInManufacturerId() {
        AuthenticationSessionDetails authenticationSessionDetails = AuthenticationSessionDetails.getInstance();
        return authenticationSessionDetails.getAuthenticatedUserId();
    }
}
