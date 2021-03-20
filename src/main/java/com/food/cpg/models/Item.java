package com.food.cpg.models;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Item {
    private Integer itemId;
    private Integer manufacturerId;
    private String itemName;
    private Double itemCookingCost;
    private Double itemTotalCost;
    private List<ItemIngredient> itemIngredients;
    private Map<String, String> errors = new HashMap<>();

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(Integer manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Double getItemCookingCost() {
        return itemCookingCost;
    }

    public void setItemCookingCost(Double itemCookingCost) {
        this.itemCookingCost = itemCookingCost;
    }

    public Double getItemTotalCost() {
        return itemTotalCost;
    }

    public void setItemTotalCost(Double itemTotalCost) {
        this.itemTotalCost = itemTotalCost;
    }

    public List<ItemIngredient> getItemIngredients() {
        return itemIngredients;
    }

    public void setItemIngredients(List<ItemIngredient> itemIngredients) {
        this.itemIngredients = itemIngredients;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    public void addItemIngredients(ItemIngredient itemIngredient) {
        if (this.itemIngredients == null) {
            this.itemIngredients = new ArrayList<>();
        }
        itemIngredient.setItemId(this.itemId);
        this.itemIngredients.add(itemIngredient);
    }


    //
//    public ItemIngredient getItemIngredients() {
//        return itemIngredients;
//    }
//
//    public void setItemIngredients(ItemIngredient itemIngredients) {
//        this.itemIngredients = itemIngredients;
//    }
//
//
//    public String getTempRaw() {
//        return tempRaw;
//    }
//
//    public void setTempRaw(String tempRaw) {
//        this.tempRaw = tempRaw;
//    }
//
//    public int getQuantity() {
//        return quantity;
//    }
//
//    public void setQuantity(int quantity) {
//        this.quantity = quantity;
//    }
//
//    public String getTempUnit() {
//        return tempUnit;
//    }
//
//    public void setTempUnit(String tempUnit) {
//        this.tempUnit = tempUnit;
//    }




    public boolean isValidItem() {
        errors = new HashMap<>();

        boolean isValid = true;

        if (StringUtils.isEmpty(getItemName())) {
            errors.put("name", "Item name is required.");
            isValid = false;
        }

//        if (StringUtils.isEmpty(getItemIngredientsList())) {
//            errors.put("contactPersonName", "Contact person is required.");
//            isValid = false;
//        }


//        if (getItemCookingCost()== null) {
//            errors.put("contactPersonPhone", "Phone number is required.");
//            isValid = false;
//        }
//
//        if (getItemTotalCost() != null && String.valueOf(getContactPersonPhone()).length() != 10) {
//            errors.put("contactPersonPhone", "Invalid phone number.");
//            isValid = false;
//        }

        return isValid;
    }

}
