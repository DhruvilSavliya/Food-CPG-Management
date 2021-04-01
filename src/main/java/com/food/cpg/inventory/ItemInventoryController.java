package com.food.cpg.inventory;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ItemInventoryController {

    private static final String REDIRECT_NOTATION = "redirect:";
    private static final String SHOW_ITEM_INVENTORY_ROUTE = "inventory/item-inventory/item-inventory";
    private static final String VIEW_ITEM_INVENTORY_KEY = "itemInventory";

    @GetMapping("/items-inventory")
    public String showItemsInventory(ItemInventory itemInventory, Model model){
        List<ItemInventory> itemInventoryList = itemInventory.getAll();
        model.addAttribute(VIEW_ITEM_INVENTORY_KEY, itemInventoryList);
        return SHOW_ITEM_INVENTORY_ROUTE;
    }
}