package com.food.cpg.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.food.cpg.constants.ApplicationConstants;
import com.food.cpg.constants.TemplateConstants;
import com.food.cpg.models.Vendor;
import com.food.cpg.services.IVendorService;
import com.food.cpg.services.impl.VendorService;

/**
 * @author Kartik Gevariya
 */
@Controller
public class VendorController {

    private final IVendorService vendorService;

    @Autowired
    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @GetMapping("/vendors")
    public String showVendors(Model model){
        List<Vendor> vendorList = vendorService.getVendorsList(1);
        if (vendorList.size() > 0) {
            model.addAttribute("vendors", vendorList);
        }
        return "vendor/vendors";
    }

    @GetMapping("/add-vendor")
    public String showAddVendorForm(Vendor vendor) {
        return "vendor/add-vendor";
    }

    @PostMapping("/save-vendor")
    public String saveVendor(Vendor vendor, BindingResult result, Model model, @RequestParam(value="action") String action) {
        if (ApplicationConstants.CANCEL.equals(action)) {
            return "redirect:/vendors";
        }

        if (result.hasErrors() || !vendor.isValidVendor()) {
            return "vendor/add-vendor";
        }

        vendorService.saveVendor(vendor);
        return "redirect:/vendors";
    }

    @PostMapping("/save-vendor/{vendorId}")
    public String updateVendor(Vendor vendor, BindingResult result, Model model,
                               @PathVariable("vendorId") int vendorId, @RequestParam(value="action") String action) {
        if (ApplicationConstants.CANCEL.equals(action)) {
            return "redirect:/vendors";
        }

        if (result.hasErrors() || !vendor.isValidVendor()) {
            return "vendor/edit-vendor";
        }

        vendorService.updateVendor(vendor);
        return "redirect:/vendors";
    }

    @GetMapping("/vendors/edit/{vendorId}")
    public String editVendor(@PathVariable("vendorId") int vendorId, Model model) {
        Vendor vendor = vendorService.getVendor(vendorId);
        model.addAttribute(TemplateConstants.VENDOR, vendor);
        return "vendor/edit-vendor";
    }

    @GetMapping("/vendors/delete/{vendorId}")
    public String deleteVendor(@PathVariable("vendorId") int vendorId, Model model) {
        vendorService.deleteVendor(vendorId);
        return "redirect:/vendors";
    }
}