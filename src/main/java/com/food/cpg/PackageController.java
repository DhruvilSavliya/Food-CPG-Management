package com.food.cpg;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Kartik Gevariya
 */
@Controller
public class PackageController {

    @GetMapping("/packages")
    public String showPackages(){
        return "packages";
    }
}