package com.food.cpg.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.food.cpg.authentication.AuthenticationSessionDetails;
import com.food.cpg.databasepersistence.PersistenceFactory;
import com.food.cpg.inventory.ItemInventory;

public class Item {

    private Integer id;
    private Integer manufacturerId;
    private String name;
    private Double cookingCost;
    private Double totalCost;
    private List<ItemRawMaterial> itemRawMaterials;

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

    public Double getCookingCost() {
        return cookingCost;
    }

    public void setCookingCost(Double cookingCost) {
        this.cookingCost = cookingCost;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public List<ItemRawMaterial> getItemRawMaterials() {
        return itemRawMaterials;
    }

    public void setItemRawMaterials(List<ItemRawMaterial> itemRawMaterials) {
        this.itemRawMaterials = itemRawMaterials;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    public List<Item> getAll() {
        int loggedInManufacturerId = getLoggedInManufacturerId();
        return getPersistence().getAll(loggedInManufacturerId);
    }

    public void save() {
        int loggedInManufacturerId = getLoggedInManufacturerId();
        this.setManufacturerId(loggedInManufacturerId);
        Integer itemId = getPersistence().save(this);
        for (ItemRawMaterial itemRawMaterial : getItemRawMaterials()) {
            itemRawMaterial.setItemId(itemId);
            itemRawMaterial.save();
        }
        saveItemInventory(itemId);
    }

    public void saveItemInventory(int itemId) {
        ItemInventory itemInventory = new ItemInventory();
        itemInventory.setItemId(itemId);
        itemInventory.setItemQuantity(0.0);
        itemInventory.save();
    }

    public void load() {
        if (this.getId() > 0) {
            getPersistence().load(this);
        }
    }

    public void delete() {
        getPersistence().delete(this.getId());
    }

    public void addItemRawMaterial(ItemRawMaterial itemRawMaterial) {
        if (this.itemRawMaterials == null) {
            this.itemRawMaterials = new ArrayList<>();
        }
        Double cost = itemRawMaterial.calculateCost();
        itemRawMaterial.setCost(cost);
        this.itemRawMaterials.add(itemRawMaterial);
    }

    public void calculateTotalCost() {
        List<ItemRawMaterial> itemRawMaterials = this.getItemRawMaterials();
        Double total = this.getCookingCost();
        for (ItemRawMaterial itemRawMaterial : itemRawMaterials) {
            total += itemRawMaterial.getCost();
        }
        this.setTotalCost(total);
    }

    private IItemPersistence getPersistence() {
        PersistenceFactory persistenceFactory = PersistenceFactory.getPersistenceFactory();
        return persistenceFactory.getItemPersistence();
    }

    private int getLoggedInManufacturerId() {
        AuthenticationSessionDetails authenticationSessionDetails = AuthenticationSessionDetails.getInstance();
        return authenticationSessionDetails.getAuthenticatedUserId();
    }
}
