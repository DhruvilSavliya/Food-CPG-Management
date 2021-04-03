package com.food.cpg.salesorder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class SalesOrderController {

    private static final String REDIRECT_NOTATION = "redirect:";
    private static final String SALES_ORDERS_REQUEST_END_POINT = "/sales-orders";
    private static final String SHOW_SALES_ORDERS_ROUTE = "sales-order/sales-orders";
    private static final String VIEW_OPEN_SALES_ORDERS_KEY = "openSalesOrders";
    private static final String VIEW_PACKAGED_SALES_ORDERS_KEY = "packagedSalesOrders";
    private static final String VIEW_SHIPPED_SALES_ORDERS_KEY = "shippedSalesOrders";
    private static final String VIEW_PAID_SALES_ORDERS_KEY = "paidSalesOrders";

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

    @GetMapping("/sales-order/move/{orderNumber}")
    public String moveSalesOrder(@PathVariable("orderNumber") String orderNumber, SalesOrder salesOrder) {
        salesOrder.setOrderNumber(orderNumber);
        salesOrder.load();
        salesOrder.moveOrderToNextStage();

        return redirectToSalesOrders();
    }

    private String redirectToSalesOrders() {
        return REDIRECT_NOTATION + SALES_ORDERS_REQUEST_END_POINT;
    }
}