package com.food.cpg.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Kartik Gevariya
 */
@Controller
public class SalesOrderController {

    @GetMapping("/sales-orders")
    public String showSalesOrders(){
        return "sales-orders";
    }
}