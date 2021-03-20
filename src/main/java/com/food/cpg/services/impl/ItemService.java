package com.food.cpg.services.impl;

import com.food.cpg.dao.IItemDAO;
import com.food.cpg.dao.IItemIngredientDAO;
import com.food.cpg.dao.impl.ItemDAOImpl;
import com.food.cpg.dao.impl.ItemIngredientDAOImpl;
import com.food.cpg.models.Item;
import com.food.cpg.models.ItemIngredient;
import com.food.cpg.models.RawMaterial;
import com.food.cpg.services.IItemService;
import com.food.cpg.services.IRawMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ListIterator;

@Service
public class ItemService implements IItemService {

    private final IItemDAO itemDAO;
    private final IItemIngredientDAO itemIngredientDAO;
    private final IRawMaterialService rawMaterialService;
    RawMaterial rawMaterial;

    @Autowired
    public ItemService(ItemDAOImpl itemDAO, ItemIngredientDAOImpl itemIngredientDAO, RawMaterialService rawMaterialService) {

        this.itemDAO = itemDAO;
        this.itemIngredientDAO = itemIngredientDAO;
        this.rawMaterialService = rawMaterialService;
    }

    @Override
    public List<Item> getItemsList(int manufacturerId) {
        return itemDAO.getItemsList(manufacturerId);
    }

    @Override
    public void saveItem(Item item) {

        itemDAO.saveItem(item);
        Integer itemId = itemDAO.getItemIdByName(item.getItemName());
        itemIngredientDAO.saveItemIngredient(item.getItemIngredients(), itemId);
    }

    @Override
    public Item getItem(int itemId) {
        return itemDAO.getItem(itemId);
    }

    @Override
    public void updateItem(Item item) { itemDAO.updateItem(item);
    }

    @Override
    public void deleteItem(int itemId) {

        itemDAO.deleteItem(itemId);
    }

    @Override
    public Double calculateTotal(Item item) {
        System.out.println("calculate call");
       List<ItemIngredient> itemIngredients= item.getItemIngredients();
       Double total= item.getItemCookingCost();
        System.out.println(total);
       for(ItemIngredient ingredient : itemIngredients ) {
           rawMaterial = rawMaterialService.getRawMaterial(ingredient.getRawMaterialId());
           total += rawMaterial.getUnitCost();
       }

        System.out.println(total);
       return total;

    }

}
