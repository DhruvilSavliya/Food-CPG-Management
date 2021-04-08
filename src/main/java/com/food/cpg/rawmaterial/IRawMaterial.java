package com.food.cpg.rawmaterial;

import java.util.List;
import java.util.Map;

public interface IRawMaterial {
    public Integer getId();

    public void setId(Integer id);

    public Integer getManufacturerId();

    public void setManufacturerId(Integer manufacturerId);

    public String getName();

    public void setName(String name);

    public Integer getVendorId();

    public void setVendorId(Integer vendorId);

    public Double getUnitCost();

    public void setUnitCost(Double unitCost);

    public Double getUnitMeasurement();

    public void setUnitMeasurement(Double unitMeasurement);

    public String getUnitMeasurementUOM();

    public void setUnitMeasurementUOM(String unitMeasurementUOM);

    public Double getReorderPointQuantity();

    public void setReorderPointQuantity(Double reorderPointQuantity);

    public String getReorderPointQuantityUOM();

    public void setReorderPointQuantityUOM(String reorderPointQuantityUOM);

    public Map<String, String> getErrors();

    public void setErrors(Map<String, String> errors);

    public boolean isValidRawMaterial();

    public List<RawMaterial> getAll();

    public void save();

    public void saveRawMaterialInventory(Integer rawMaterialID);

    public void load();

    public void update();

    public void delete();

    public double getCost(int rawMaterialId);

}
