package com.food.cpg.salesorder;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Kartik Gevariya
 */
@Controller
public class SalesOrderController {

    private static final String SHOW_SALES_ORDERS_ROUTE = "sales-order/sales-orders";

    @GetMapping("/sales-orders")
    public String showSalesOrders(){
        return SHOW_SALES_ORDERS_ROUTE;
    }
}