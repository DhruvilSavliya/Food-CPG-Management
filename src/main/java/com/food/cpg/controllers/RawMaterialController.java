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
import com.food.cpg.models.RawMaterial;
import com.food.cpg.models.Unit;
import com.food.cpg.services.IRawMaterialService;
import com.food.cpg.services.IVendorService;
import com.food.cpg.services.impl.RawMaterialService;
import com.food.cpg.services.impl.VendorService;

/**
 * @author Kartik Gevariya
 */
@Controller
public class RawMaterialController {

    private final IRawMaterialService rawMaterialService;
    private final IVendorService vendorService;

    @Autowired
    public RawMaterialController(RawMaterialService rawMaterialService, VendorService vendorService) {
        this.rawMaterialService = rawMaterialService;
        this.vendorService = vendorService;
    }

    @GetMapping("/raw-materials")
    public String showRawMaterials(Model model){
        List<RawMaterial> rawMaterialsList = rawMaterialService.getRawMaterialsList(1);
        if (rawMaterialsList.size() > 0) {
            model.addAttribute("rawMaterials", rawMaterialsList);
        }
        return "raw-material/raw-materials";
    }

    @GetMapping("/add-raw-material")
    public String showAddRawMaterialForm(RawMaterial rawMaterial, Model model) {
        model.addAttribute("units", Unit.values());
        model.addAttribute("vendors", vendorService.getVendorsList(1));
        return "raw-material/add-raw-material";
    }

    @PostMapping("/save-raw-material")
    public String saveRawMaterial(RawMaterial rawMaterial, BindingResult result, Model model, @RequestParam(value="action") String action) {
        if (ApplicationConstants.CANCEL.equals(action)) {
            return "redirect:/raw-materials";
        }

        if (result.hasErrors() || !rawMaterial.isValidRawMaterial()) {
            model.addAttribute("units", Unit.values());
            model.addAttribute("vendors", vendorService.getVendorsList(1));
            return "raw-material/add-raw-material";
        }

        rawMaterialService.saveRawMaterial(rawMaterial);
        return "redirect:/raw-materials";
    }

    @PostMapping("/save-raw-material/{rawMaterialId}")
    public String updateRawMaterial(RawMaterial rawMaterial, BindingResult result, Model model,
                               @PathVariable("rawMaterialId") int rawMaterialId, @RequestParam(value="action") String action) {
        if (ApplicationConstants.CANCEL.equals(action)) {
            return "redirect:/raw-materials";
        }

        if (result.hasErrors() || !rawMaterial.isValidRawMaterial()) {
            return "raw-material/edit-raw-material";
        }

        rawMaterialService.updateRawMaterial(rawMaterial);
        return "redirect:/raw-materials";
    }

    @GetMapping("/raw-materials/edit/{rawMaterialId}")
    public String editRawMaterial(@PathVariable("rawMaterialId") int rawMaterialId, Model model) {
        RawMaterial rawMaterial = rawMaterialService.getRawMaterial(rawMaterialId);
        model.addAttribute(TemplateConstants.RAW_MATERIAL, rawMaterial);
        model.addAttribute("units", Unit.values());
        model.addAttribute("vendors", vendorService.getVendorsList(1));
        return "raw-material/edit-raw-material";
    }

    @GetMapping("/raw-materials/delete/{rawMaterialId}")
    public String deleteRawMaterial(@PathVariable("rawMaterialId") int rawMaterialId, Model model) {
        rawMaterialService.deleteRawMaterial(rawMaterialId);
        return "redirect:/raw-materials";
    }
}