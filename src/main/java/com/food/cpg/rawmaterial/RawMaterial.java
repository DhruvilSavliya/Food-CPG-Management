package com.food.cpg.rawmaterial;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.food.cpg.inventory.DefaultInventoryFactory;
import com.food.cpg.inventory.IRawMaterialInventory;
import com.food.cpg.inventory.InventoryFactory;
import org.springframework.util.StringUtils;
import com.food.cpg.authentication.AuthenticationSessionDetails;
import com.food.cpg.databasepersistence.PersistenceFactory;

public class RawMaterial {
    private Integer id;
    private Integer manufacturerId;
    private String name;
    private Integer vendorId;
    private Double unitCost;
    private Double unitMeasurement;
    private String unitMeasurementUOM;
    private Double reorderPointQuantity;
    private String reorderPointQuantityUOM;

    private Map<String, String> errors = new HashMap<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(Integer manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public Double getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(Double unitCost) {
        this.unitCost = unitCost;
    }

    public Double getUnitMeasurement() {
        return unitMeasurement;
    }

    public void setUnitMeasurement(Double unitMeasurement) {
        this.unitMeasurement = unitMeasurement;
    }

    public String getUnitMeasurementUOM() {
        return unitMeasurementUOM;
    }

    public void setUnitMeasurementUOM(String unitMeasurementUOM) {
        this.unitMeasurementUOM = unitMeasurementUOM;
    }

    public Double getReorderPointQuantity() {
        return reorderPointQuantity;
    }

    public void setReorderPointQuantity(Double reorderPointQuantity) {
        this.reorderPointQuantity = reorderPointQuantity;
    }

    public String getReorderPointQuantityUOM() {
        return reorderPointQuantityUOM;
    }

    public void setReorderPointQuantityUOM(String reorderPointQuantityUOM) {
        this.reorderPointQuantityUOM = reorderPointQuantityUOM;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    public boolean isValidRawMaterial() {
        errors = new HashMap<>();

        boolean isValid = true;

        if (StringUtils.isEmpty(this.getName())) {
            errors.put("name", "Raw material name is required.");
            isValid = false;
        }

        if (this.getVendorId() == null) {
            errors.put("vendor", "Valid vendor is required.");
            isValid = false;
        }

        if (this.getUnitCost() == null) {
            errors.put("unitCost", "Unit cost is required.");
            isValid = false;
        }

        if (this.getUnitMeasurement() == null || StringUtils.isEmpty(this.getUnitMeasurementUOM())) {
            errors.put("unitMeasurement", "Unit measurement is required.");
            isValid = false;
        }

        if (this.getReorderPointQuantity() == null || StringUtils.isEmpty(this.getReorderPointQuantityUOM())) {
            errors.put("reorderPointQuantity", "Reorder point quantity is required.");
            isValid = false;
        }

        return isValid;
    }

    public List<RawMaterial> getAll() {
        int loggedInManufacturerId = getLoggedInManufacturerId();
        return getPersistence().getAll(loggedInManufacturerId);
    }

    public void save() {
        int loggedInManufacturerId = getLoggedInManufacturerId();
        this.setManufacturerId(loggedInManufacturerId);

        Integer rawMaterialId = getPersistence().save(this);

        saveRawMaterialInventory(rawMaterialId);
    }

    public void saveRawMaterialInventory(Integer rawMaterialID){
        IRawMaterialInventory rawMaterialInventory = InventoryFactory.instance().makeRawMaterialInventory();
        rawMaterialInventory.save(rawMaterialID);
    }

    public void load() {
        if (this.getId() > 0) {
            getPersistence().load(this);
        }
    }

    public void update() {
        getPersistence().update(this);
    }

    public void delete() {
        getPersistence().delete(this.getId());
    }

    public double getCost(int rawMaterialId) {
        List<RawMaterial> rawMaterials = getAll();
        for (RawMaterial rawMaterial : rawMaterials) {
            if (rawMaterial.getId() == rawMaterialId) {
                return rawMaterial.getUnitCost();
            }
        }
        return 0.0;
    }

    private IRawMaterialPersistence getPersistence() {
        PersistenceFactory persistenceFactory = PersistenceFactory.getPersistenceFactory();
        return persistenceFactory.getRawMaterialPersistence();
    }

    private int getLoggedInManufacturerId() {
        AuthenticationSessionDetails authenticationSessionDetails = AuthenticationSessionDetails.getInstance();
        return authenticationSessionDetails.getAuthenticatedUserId();
    }
}
