package com.food.cpg.inventory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Kartik Gevariya
 */
@Controller
public class RawMaterialInventoryController {

    @GetMapping("/raw-materials-inventory")
    public String showRawMaterialsInventory(){
        return "raw-materials-inventory";
    }
}