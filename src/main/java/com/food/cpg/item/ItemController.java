package com.food.cpg.item;

import com.food.cpg.models.Unit;
import com.food.cpg.purchaseorder.PurchaseOrder;
import com.food.cpg.rawmaterial.RawMaterial;
import com.food.cpg.vendor.Vendor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ItemController {

    private static final String REDIRECT_NOTATION = "redirect:";
    private static final String ITEM_LIST_REQUEST_END_POINT = "/items";
    private static final String SHOW_ITEMS_ROUTE = "item/items";
    private static final String SHOW_ADD_ITEM_FORM_ROUTE = "item/add-item";
    private static final String VIEW_ITEMS_KEY = "items";
    private static final String VIEW_RAW_MATERIALS_KEY = "rawMaterials";
    private static final String VIEW_UNITS_KEY = "units";
    private static final String VIEW_VENDORS_KEY = "vendors";

    @GetMapping("/items")
    public String showItems(Item item, Model model) {
        List<Item> itemList = item.getAll();
        model.addAttribute(VIEW_ITEMS_KEY, itemList);
        return SHOW_ITEMS_ROUTE;
    }

    @GetMapping("/add-item")
    public String addItem(Item item, ItemRawMaterial itemRawMaterial, RawMaterial rawMaterial, Vendor vendor, Model model) {
        model.addAttribute(VIEW_UNITS_KEY, Unit.values());
        model.addAttribute(VIEW_VENDORS_KEY, vendor.getAll());
        model.addAttribute(VIEW_RAW_MATERIALS_KEY, rawMaterial.getAll());
        return SHOW_ADD_ITEM_FORM_ROUTE;
    }

    @PostMapping("/add-item-raw-material")
    public String addItemRawMaterial(Item item, ItemRawMaterial itemRawMaterial, RawMaterial rawMaterial, Vendor vendor, Model model) {
        item.addItemRawMaterial(itemRawMaterial);
        model.addAttribute(VIEW_UNITS_KEY, Unit.values());
        model.addAttribute(VIEW_VENDORS_KEY, vendor.getAll());
        model.addAttribute(VIEW_RAW_MATERIALS_KEY, rawMaterial.getAll());
        return SHOW_ADD_ITEM_FORM_ROUTE;
    }

    @PostMapping("/calculate-total-item-cost")
    public String calculateTotalCost(Item item, ItemRawMaterial itemRawMaterial, RawMaterial rawMaterial, Vendor vendor, Model model) {
        item.calculateTotalCost();
        model.addAttribute(VIEW_UNITS_KEY, Unit.values());
        model.addAttribute(VIEW_VENDORS_KEY, vendor.getAll());
        model.addAttribute(VIEW_RAW_MATERIALS_KEY, rawMaterial.getAll());
        return SHOW_ADD_ITEM_FORM_ROUTE;
    }

    @PostMapping("/save-item")
    public String saveItem(Item item) {
        item.save();
        return redirectToItems();
    }

    @GetMapping("/item/delete/{id}")
    public String deleteItem(@PathVariable("id") int itemId, Item item, ItemRawMaterial itemRawMaterial) {
        item.setId(itemId);
        itemRawMaterial.setItemId(itemId);
        item.delete();
        itemRawMaterial.delete();
        return redirectToItems();
    }


    private String redirectToItems() {
        return REDIRECT_NOTATION + ITEM_LIST_REQUEST_END_POINT;
    }
}