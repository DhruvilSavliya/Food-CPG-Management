package com.food.cpg.models;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Item {
    private int itemId;
    private String itemName;
    private Double itemCookingCost;
    private Double itemTotalCost;
    private List<ItemIngredient> itemIngredientsList = new ArrayList<ItemIngredient>();
    private Map<String, String> errors = new HashMap<>();


//    private ItemIngredient itemIngredients;
//    private String tempRaw;
//    private int quantity;
//    private String tempUnit;


    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getItemCookingCost() {
        return itemCookingCost;
    }

    public void setItemCookingCost(Double itemCookingCost) {
        this.itemCookingCost = itemCookingCost;
    }

    public double getItemTotalCost() {
        return itemTotalCost;
    }

    public void setItemTotalCost(Double itemTotalCost) {
        this.itemTotalCost = itemTotalCost;
    }

    public List<ItemIngredient> getItemIngredientsList() {
        return itemIngredientsList;
    }

    public void setItemIngredientsList(List<ItemIngredient> itemIngredientsList) {
        this.itemIngredientsList = itemIngredientsList;
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

    public void addIngredient( ItemIngredient itemIngredients){
        itemIngredientsList.add(itemIngredients);
    }



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
