package com.food.cpg.inventory;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ItemInventoryController {

    private static final String REDIRECT_NOTATION = "redirect:";
    private static final String SHOW_ITEM_INVENTORY_ROUTE = "inventory/item-inventory/item-inventory";
    private static final String ITEM_INVENTORY_LIST_REQUEST_END_POINT = "/items-inventory";
    private static final String VIEW_ITEM_INVENTORY_KEY = "itemInventory";

    @GetMapping("/items-inventory")
    public String showItemsInventory(ItemInventory itemInventory, Model model){
        List<ItemInventory> itemInventoryList = itemInventory.getAll();
        model.addAttribute(VIEW_ITEM_INVENTORY_KEY, itemInventoryList);
        return SHOW_ITEM_INVENTORY_ROUTE;
    }

//    @PostMapping("/save-raw-materials-inventory-quantity")
//    public String saveItemInventoryQuantity(ItemInventory itemInventory, Model model){
//        itemInventory.save();
//        return redirectToItemInventory();
//    }

    private String redirectToItemInventory() {
        return REDIRECT_NOTATION + ITEM_INVENTORY_LIST_REQUEST_END_POINT;
    }
}