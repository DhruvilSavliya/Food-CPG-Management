package com.food.cpg;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Kartik Gevariya
 */
@Controller
public class RawMaterialController {

    @GetMapping("/raw-materials")
    public String showRawMaterials(){
        return "raw-materials";
    }
}