package com.food.cpg.controllers;

import com.food.cpg.constants.ApplicationConstants;
import com.food.cpg.models.Item;
import com.food.cpg.models.ItemIngredient;
import com.food.cpg.models.Unit;
import com.food.cpg.services.IItemService;
import com.food.cpg.services.IRawMaterialService;
import com.food.cpg.services.impl.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Controller
public class ItemController {


    private static final Logger LOG = LoggerFactory.getLogger(ItemController.class);
    private final IItemService itemService;
    private final IRawMaterialService rawMaterialService;
//    private final Item item;

    public ItemController(IRawMaterialService rawMaterialService, ItemService itemService) {
        this.itemService = itemService;
        this.rawMaterialService = rawMaterialService;
//        this.item = item;
    }


    @GetMapping("/items")
    public String showItems(Model model){
        List<Item> itemList = itemService.getItemsList(1);
        if (itemList.size() > 0) {
            model.addAttribute("items", itemList);
        }
        return "item/items";
    }

    @GetMapping("/add-item")
    public String addItem(Item item, Model model){
        for (int i=1;i<=3;i++){
            item.addIngredient(new ItemIngredient());
        }
        model.addAttribute("item", item);
        model.addAttribute("units", Unit.values());
        model.addAttribute("rawMaterials", rawMaterialService.getRawMaterialsList(1));
        return "item/add-item";
    }

    @PostMapping("/save-item")
    public String saveItem(Item item, ItemIngredient itemIngredient, BindingResult result, Model model, @RequestParam(value="action") String action){

//        item.setItemName("Egg sandwich");
//        List<ItemIngredient> itemIngredientsList1 = new ArrayList<ItemIngredient>();
//        itemIngredientsList1.add(new ItemIngredient(1,3,"Pc"));
//        itemIngredientsList1.add(new ItemIngredient(5,10,"g"));
//        itemIngredientsList1.add(new ItemIngredient(9,2,"lb"));
//        itemIngredientsList1.add(new ItemIngredient(14,1,"Pc"));
//        item.setItemIngredientsList(itemIngredientsList1);
//        item.setItemCookingCost(3.99);
//        item.setItemTotalCost(17.99);
//
//        LOG.info("Item ingredient set", itemIngredientsList1.size());
//        LOG.info("Item", item);


        if (ApplicationConstants.CANCEL.equals(action)) {
            return "redirect:/items";
        }

        if (result.hasErrors() || !item.isValidItem()) {
            return "item/items";
        }

        itemService.saveItem(item);
        return "item/items";
    }

}