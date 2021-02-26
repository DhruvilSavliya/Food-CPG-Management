package com.food.cpg.controllers;


import com.food.cpg.constants.TemplateConstants;
import com.food.cpg.models.Manufacturer;
import com.food.cpg.services.ImanufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.food.cpg.services.impl.ManufacturerService;


import java.util.List;


/**
 * @author Dhruvilkumar Savliya
 */
@Controller
public class AdminController {

    private final ImanufacturerService manufacturerService;

    @Autowired
    public AdminController(ManufacturerService manufacturerService){ this.manufacturerService = manufacturerService; }


    @GetMapping("/admin-login")
    public String adminlogin()
    {
        return "admin/admin_login";
    }

    @GetMapping("/admin")
    public String admin(Model model)
    {
        List<Manufacturer> manufacturerList = manufacturerService.getManufacturerList(1);
        if (manufacturerList.size() > 0) {
            model.addAttribute("manufacturers", manufacturerList);
        }
        return "admin/admin";
    }

    @GetMapping("/approve-manufacturer/{manufacturerId}")
    public String approveManufacturer(@PathVariable("manufacturerId") int manufacturerId, Model model) {
       manufacturerService.approveManufacturer(manufacturerId);
        return "redirect:/admin";
    }

    @GetMapping("/block-manufacturer/{manufacturerId}")
    public String blockManufacturer(@PathVariable("manufacturerId") int manufacturerId, Model model) {
        manufacturerService.blockManufacturer(manufacturerId);
        return "redirect:/admin";
    }

    @GetMapping("manufacturer-details/{manufacturerId}")
    public String details(@PathVariable("manufacturerId") int manufacturerId, Model model) {
        Manufacturer manufacturer = manufacturerService.getManufacturer(manufacturerId);
        model.addAttribute(TemplateConstants.MANUFACTURER, manufacturer);
        return "admin/manufacturer-details";
    }

}