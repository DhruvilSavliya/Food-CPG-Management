package com.food.cpg.manufactureorder;

import com.food.cpg.item.IItem;
import com.food.cpg.item.Item;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.food.cpg.inventory.Unit;

@Controller
public class ManufactureOrderController {

    private static final String REDIRECT_NOTATION = "redirect:";
    private static final String MANUFACTURE_ORDERS_END_POINT = "/manufacture-orders";
    private static final String SHOW_MANUFACTURE_ORDERS_ROUTE = "manufacture-order/manufacture-orders";
    private static final String SHOW_ADD_MANUFACTURE_ORDER_FORM_ROUTE = "manufacture-order/add-manufacture-order";
    private static final String VIEW_OPEN_MANUFACTURE_ORDERS_KEY = "openManufactureOrders";
    private static final String VIEW_MANUFACTURED_MANUFACTURE_ORDERS_KEY = "manufacturedManufactureOrders";
    private static final String VIEW_PACKAGED_MANUFACTURE_ORDERS_KEY = "packagedManufactureOrders";

    private static final String VIEW_ITEMS_KEY = "items";
    private static final String VIEW_UNITS_KEY = "units";

    @GetMapping("/manufacture-orders")
    public String showManufactureOrders(ManufactureOrder manufactureOrder, Model model) {
        model.addAttribute(VIEW_OPEN_MANUFACTURE_ORDERS_KEY, manufactureOrder.getAllOpenOrders());
        model.addAttribute(VIEW_MANUFACTURED_MANUFACTURE_ORDERS_KEY, manufactureOrder.getAllManufacturedOrders());
        model.addAttribute(VIEW_PACKAGED_MANUFACTURE_ORDERS_KEY, manufactureOrder.getAllPackagedOrders());
        return SHOW_MANUFACTURE_ORDERS_ROUTE;

    }

    @GetMapping("/manufacture-order/delete/{orderNumber}")
    public String deleteSalesOrder(@PathVariable("orderNumber") String orderNumber, ManufactureOrder manufactureOrder) {
        manufactureOrder.setOrderNumber(orderNumber);
        manufactureOrder.delete();

        return redirectToManufactureOrders();
    }

    @GetMapping("/manufacture-order/move/{orderNumber}")
    public String moveSalesOrder(@PathVariable("orderNumber") String orderNumber, ManufactureOrder manufactureOrder) {
        manufactureOrder.setOrderNumber(orderNumber);
        manufactureOrder.load();
        manufactureOrder.moveOrderToNextStage();

        return redirectToManufactureOrders();
    }

    @GetMapping("/add-manufacture-order")
    public String addManufactureOrderForm(ManufactureOrder manufactureOrder, Item item, Model model) {
        model.addAttribute(VIEW_UNITS_KEY, Unit.values());
        model.addAttribute(VIEW_ITEMS_KEY, item.getAll());
        return SHOW_ADD_MANUFACTURE_ORDER_FORM_ROUTE;
    }

    @PostMapping("/calculate-total")
    public String calculateTotalCost(ManufactureOrder manufactureOrder, Item item, Model model) {
        manufactureOrder.calculateTotalCost();
        model.addAttribute(VIEW_UNITS_KEY, Unit.values());
        model.addAttribute(VIEW_ITEMS_KEY, item.getAll());
        return SHOW_ADD_MANUFACTURE_ORDER_FORM_ROUTE;
    }

    @PostMapping("/save-manufacture-order")
    public String saveManufactureOrder(ManufactureOrder manufactureOrder) {
        manufactureOrder.save();
        return redirectToManufactureOrders();
    }

    private String redirectToManufactureOrders() {
        return REDIRECT_NOTATION + MANUFACTURE_ORDERS_END_POINT;
    }
}