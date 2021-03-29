package com.food.cpg.inventory;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class RawMaterialInventoryController {

    private static final String SHOW_RAW_MATERIAL_INVENTORY_ROUTE = "inventory/raw-material-inventory/raw-material-inventory";
    private static final String VIEW_RAW_MATERIAL_INVENTORY_KEY = "rawMaterialInventory";

    @GetMapping("/raw-materials-inventory")
    public String showRawMaterialsInventory(RawMaterialInventory rawMaterialInventory, Model model){
        List<RawMaterialInventory> rawMaterialInventoryList = rawMaterialInventory.getAll();
        model.addAttribute(VIEW_RAW_MATERIAL_INVENTORY_KEY, rawMaterialInventoryList);
        return SHOW_RAW_MATERIAL_INVENTORY_ROUTE;
    }
}