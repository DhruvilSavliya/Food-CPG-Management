package com.food.cpg.item;

import java.util.List;
import java.util.Map;

public interface IItem {

    public Integer getId();

    public void setId(Integer id);

    public Integer getManufacturerId();

    public void setManufacturerId(Integer manufacturerId);

    public String getName();

    public void setName(String name);

    public Double getCookingCost();

    public void setCookingCost(Double cookingCost);

    public Double getTotalCost();

    public void setTotalCost(Double totalCost);

    public List<ItemRawMaterial> getItemRawMaterials();

    public void setItemRawMaterials(List<ItemRawMaterial> itemRawMaterials);

    public Map<String, String> getErrors();

    public void setErrors(Map<String, String> errors);

    public List<IItem> getAll();

    public void save();

    public void saveItemInventory(int itemId);

    public void load();

    public void delete();

    public void addItemRawMaterial(ItemRawMaterial itemRawMaterial);

    public void calculateTotalCost();
}
