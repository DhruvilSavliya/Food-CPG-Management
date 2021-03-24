package com.food.cpg.vendor;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class VendorController {

    private static final String REDIRECT_NOTATION = "redirect:";
    private static final String VENDOR_LIST_REQUEST_END_POINT = "/vendors";
    private static final String VENDOR_LIST_ROUTE = "vendor/vendors";
    private static final String NEW_VENDOR_FORM_ROUTE = "vendor/add-vendor";
    private static final String EDIT_VENDOR_FORM_ROUTE = "vendor/edit-vendor";
    private static final String VIEW_VENDORS_KEY = "vendors";

    @GetMapping("/vendors")
    public String showVendors(Vendor vendor, Model model) {
        List<Vendor> vendorList = vendor.getAll();
        model.addAttribute(VIEW_VENDORS_KEY, vendorList);

        return VENDOR_LIST_ROUTE;
    }

    @GetMapping("/add-vendor")
    public String showAddVendorForm(Vendor vendor) {
        return NEW_VENDOR_FORM_ROUTE;
    }

    @PostMapping("/save-vendor")
    public String saveVendor(Vendor vendor, BindingResult result) {
        if (result.hasErrors() || !vendor.isValidVendor()) {
            return NEW_VENDOR_FORM_ROUTE;
        }

        vendor.save();
        return redirectToVendorList();
    }

    @PostMapping("/save-vendor/{vendorId}")
    public String editVendor(Vendor vendor, BindingResult result) {
        if (result.hasErrors() || !vendor.isValidVendor()) {
            return EDIT_VENDOR_FORM_ROUTE;
        }

        vendor.update();
        return redirectToVendorList();
    }

    @GetMapping("/vendors/edit/{vendorId}")
    public String showEditVendorForm(@PathVariable("vendorId") int vendorId, Vendor vendor) {
        vendor.setId(vendorId);
        vendor.load();

        return EDIT_VENDOR_FORM_ROUTE;
    }

    @GetMapping("/vendors/delete/{vendorId}")
    public String deleteVendor(@PathVariable("vendorId") int vendorId, Vendor vendor) {
        vendor.setId(vendorId);
        vendor.delete();

        return redirectToVendorList();
    }

    private String redirectToVendorList() {
        return REDIRECT_NOTATION + VENDOR_LIST_REQUEST_END_POINT;
    }
}