package com.food.cpg.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Kartik Gevariya
 */
@Controller
public class ManufactureOrderController {

    @GetMapping("/manufacture-orders")
    public String showManufactureOrders(){
        return "manufacture-orders";
    }
}