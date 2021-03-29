package com.food.cpg.inventory;

import com.food.cpg.authentication.AuthenticationSessionDetails;
import com.food.cpg.databasepersistence.PersistenceFactory;
import com.food.cpg.item.IItemPersistence;
import com.food.cpg.item.Item;

import java.util.List;

public class RawMaterialInventory {

    private Integer rawMaterialId;
    private String rawMaterialName;
    private String vendorName;
    private Double rawMaterialQuantity;
    private String rawMaterialQuantityUOM;
    private Integer manufacturerId;
    private String manufacturerEmail;

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

    public String getManufacturerEmail() {
        return manufacturerEmail;
    }

    public void setManufacturerEmail(String manufacturerEmail) {
        this.manufacturerEmail = manufacturerEmail;
    }

    public List<RawMaterialInventory> getAll() {
    int loggedInManufacturerId = getLoggedInManufacturerId();
    return getPersistence().getAll(loggedInManufacturerId);
     }

    private IRawMaterialInventoryPersistence getPersistence() {
        PersistenceFactory persistenceFactory = PersistenceFactory.getPersistenceFactory();
        return persistenceFactory.getRawMaterialInventoryPersistence();
    }

    private int getLoggedInManufacturerId() {
        AuthenticationSessionDetails authenticationSessionDetails = AuthenticationSessionDetails.getInstance();
        return authenticationSessionDetails.getAuthenticatedUserId();
    }
}
