package com.food.cpg.salesorder;

import com.food.cpg.item.Item;
import com.food.cpg.manufacturingorder.ManufactureOrder;
import com.food.cpg.models.Unit;
import com.food.cpg.packaging.Packages;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SalesOrderController {

    private static final String REDIRECT_NOTATION = "redirect:";
    private static final String SALES_ORDERS_REQUEST_END_POINT = "/sales-orders";
    private static final String SHOW_SALES_ORDERS_ROUTE = "sales-order/sales-orders";
    private static final String SHOW_ADD_SALES_ORDER_FORM_ROUTE = "sales-order/add-sales-order";
    private static final String VIEW_OPEN_SALES_ORDERS_KEY = "openSalesOrders";
    private static final String VIEW_PACKAGED_SALES_ORDERS_KEY = "packagedSalesOrders";
    private static final String VIEW_SHIPPED_SALES_ORDERS_KEY = "shippedSalesOrders";
    private static final String VIEW_PAID_SALES_ORDERS_KEY = "paidSalesOrders";

    private static final String VIEW_ITEMS_KEY = "items";
    private static final String VIEW_PACKAGES_KEY = "packages";

    @GetMapping("/sales-orders")
    public String showSalesOrders(SalesOrder salesOrder, Model model) {
        model.addAttribute(VIEW_OPEN_SALES_ORDERS_KEY, salesOrder.getAllOpenOrders());
        model.addAttribute(VIEW_PACKAGED_SALES_ORDERS_KEY, salesOrder.getAllPackagedOrders());
        model.addAttribute(VIEW_SHIPPED_SALES_ORDERS_KEY, salesOrder.getAllShippedOrders());
        model.addAttribute(VIEW_PAID_SALES_ORDERS_KEY, salesOrder.getAllPaidOrders());
        return SHOW_SALES_ORDERS_ROUTE;
    }

    @GetMapping("/sales-order/delete/{orderNumber}")
    public String deleteSalesOrder(@PathVariable("orderNumber") String orderNumber, SalesOrder salesOrder) {
        salesOrder.setOrderNumber(orderNumber);
        salesOrder.delete();

        return redirectToSalesOrders();
    }

    @GetMapping("/add-sales-order")
    public String addSalesOrderForm(SalesOrder salesOrder, Item item, Packages packages, Model model) {
//        model.addAttribute(VIEW_UNITS_KEY, Unit.values());
        model.addAttribute(VIEW_ITEMS_KEY, item.getAll());
        model.addAttribute(VIEW_PACKAGES_KEY, packages.getAll());
        return SHOW_ADD_SALES_ORDER_FORM_ROUTE;
    }

    @GetMapping("/sales-order/move/{orderNumber}")
    public String moveSalesOrder(@PathVariable("orderNumber") String orderNumber, SalesOrder salesOrder) {
        salesOrder.setOrderNumber(orderNumber);
        salesOrder.load();
        salesOrder.moveOrderToNextStage();

        return redirectToSalesOrders();
    }

    @PostMapping("/calculate-sales-total")
    public String calculateTotalCost(SalesOrder salesOrder, Item item, Model model) {
        salesOrder.calculateTotalCost();
        model.addAttribute(VIEW_ITEMS_KEY, item.getAll());
        return SHOW_ADD_SALES_ORDER_FORM_ROUTE;
    }

    @PostMapping("/save-sales-order")
    public String saveSalesOrder(SalesOrder salesOrder){
        salesOrder.save();
        return redirectToSalesOrders();
    }

    private String redirectToSalesOrders() {
        return REDIRECT_NOTATION + SALES_ORDERS_REQUEST_END_POINT;
    }
}