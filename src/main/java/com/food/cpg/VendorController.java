package com.food.cpg;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Kartik Gevariya
 */
@Controller
public class VendorController {

    @GetMapping("/vendors")
    public String showVendors(){
        return "vendors";
    }
}