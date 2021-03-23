package com.food.cpg.manufacturingorder;

import com.food.cpg.item.Item;
import com.food.cpg.item.ItemRawMaterial;
import com.food.cpg.models.Unit;
import com.food.cpg.purchaseorder.PurchaseOrder;
import com.food.cpg.purchaseorder.PurchaseOrderRawMaterial;
import com.food.cpg.rawmaterial.RawMaterial;
import com.food.cpg.vendor.Vendor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;

@Controller
public class ManufactureOrderController {

    private static final String REDIRECT_NOTATION = "redirect:";
    private static final String MANUFACTURE_ORDERS_END_POINT = "/manufacture-orders";
    private static final String SHOW_MANUFACTURE_ORDERS_ROUTE = "manufacture-order/manufacture-orders";
    private static final String SHOW_ADD_MANUFACTURE_ORDER_FORM_ROUTE = "manufacture-order/add-manufacture-order";
    private static final String VIEW_MANUFACTURE_ORDERS_KEY = "manufactureOrders";
    private static final String VIEW_ITEMS_KEY = "items";
    private static final String VIEW_UNITS_KEY = "units";

    @GetMapping("/manufacture-orders")
    public String showManufactureOrders(Model model){
        model.addAttribute(VIEW_MANUFACTURE_ORDERS_KEY, new ArrayList<>());
        return SHOW_MANUFACTURE_ORDERS_ROUTE;
    }

    @GetMapping("/add-manufacture-order")
    public String addManufactureOrderForm(ManufactureOrder manufactureOrder, Item item, Model model) {
        model.addAttribute(VIEW_UNITS_KEY, Unit.values());
        model.addAttribute(VIEW_ITEMS_KEY, item.getAll());
        return SHOW_ADD_MANUFACTURE_ORDER_FORM_ROUTE;
    }

    @PostMapping("/calculate-total")
    public String calculateTotalCost(ManufactureOrder manufactureOrder, Item item, Model model) {
        manufactureOrder.calculateTotalCost(item.getAll());
        model.addAttribute(VIEW_UNITS_KEY, Unit.values());
        model.addAttribute(VIEW_ITEMS_KEY, item.getAll());
        return SHOW_ADD_MANUFACTURE_ORDER_FORM_ROUTE;
    }

    @PostMapping("/save-manufacture-order")
    public String saveManufactureOrder(ManufactureOrder manufactureOrder){
        manufactureOrder.save();
        return redirectToManufactureOrders();
    }

    private String redirectToManufactureOrders() {
        return REDIRECT_NOTATION + MANUFACTURE_ORDERS_END_POINT;
    }
}