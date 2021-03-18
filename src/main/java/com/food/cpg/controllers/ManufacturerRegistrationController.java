package com.food.cpg.controllers;

import com.food.cpg.constants.ApplicationConstants;
import com.food.cpg.models.Manufacturer;
import com.food.cpg.models.User;
import com.food.cpg.services.IManufacturerService;
import com.food.cpg.services.impl.ManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class ManufacturerRegistrationController {

    private final IManufacturerService manufacturerService;


    @Autowired
    public ManufacturerRegistrationController(ManufacturerService manufacturerService) {
        this.manufacturerService = manufacturerService;
    }

    @GetMapping("/register-manufacturer")
    public String registrationForm(Manufacturer manufacturer){
        return "manufacturer/register-manufacturer";
    }

    @PostMapping("/save-manufacturer")
    public String saveManufacturer(Manufacturer manufacturer, BindingResult result, Model model, @RequestParam(required=false,value="action") String action){
        if (ApplicationConstants.CANCEL.equals(action)) {
            return "redirect:/login";
        }


        if (result.hasErrors()|| !manufacturer.isValidManufacturer()) {
            return "manufacturer/register-manufacturer";
        }

        manufacturerService.saveManufacturer(manufacturer);
        return "manufacturer/manufacturer-registration-request";
    }

}
