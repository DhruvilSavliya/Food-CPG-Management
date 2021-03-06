package com.food.cpg.controllers;

import com.food.cpg.constants.ApplicationConstants;
import com.food.cpg.constants.TemplateConstants;
import com.food.cpg.models.Packages;

import com.food.cpg.models.Unit;
import com.food.cpg.services.IPackageService;
import com.food.cpg.services.IRawMaterialService;
import com.food.cpg.services.impl.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author Dhruvilkumar Savliya
 */
@Controller
public class PackageController {

    private final IPackageService packageService;
    private final IRawMaterialService rawMaterialService;

    @Autowired
    public PackageController(PackageService packageService, IRawMaterialService rawMaterialService) {
        this.packageService = packageService;
        this.rawMaterialService = rawMaterialService;
    }

    @GetMapping("/packages")
    public String showPackages(Model model){
        List<Packages> packagesList = packageService.getPackageList(1);
        if (packagesList.size() > 0) {
            model.addAttribute("packages", packagesList);
        }
        return "packages/packages";
    }

    @GetMapping("/add-packages")
    public String showAddPackagesForm(Packages packages, Model model){
        model.addAttribute("rawMaterial", rawMaterialService.getRawMaterialsList(1));
        return "packages/add-packages";
    }

    @PostMapping("/save-packages")
    public String savePackages(Packages packages, BindingResult result, Model model, @RequestParam(value="action") String action){
        if (ApplicationConstants.CANCEL.equals(action)) {
            return "redirect:/packages";
        }

        if (result.hasErrors() || !packages.isValidPackage()) {
            model.addAttribute("rawMaterials", rawMaterialService.getRawMaterialsList(1));
            return "package/add-packages";
        }

        packageService.savePackages(packages);
        return "redirect:/packages";
    }

    @PostMapping("/save-packages/{packageId}")
    public String updatePackages(Packages packages, BindingResult result, Model model,
                                    @PathVariable("packageId") int packageId, @RequestParam(value="action") String action) {
        if (ApplicationConstants.CANCEL.equals(action)) {
            return "redirect:/packages";
        }

        if (result.hasErrors() || !packages.isValidPackage()) {
            return "packages/edit-packages";
        }

        packageService.updatePackages(packages);
        return "redirect:/packages";
    }

    @GetMapping("/packages/edit/{packageId}")
    public String editPackages(@PathVariable("packageId") int packageId, Model model) {
        Packages packages = packageService.getPackages(packageId);
        model.addAttribute(TemplateConstants.PACKAGES, packages);
        model.addAttribute("rawMaterial", rawMaterialService.getRawMaterialsList(1));
        return "packages/edit-packages";
    }

    @GetMapping("/packages/delete/{packageId}")
    public String deletePackags(@PathVariable("packageId") int packageId, Model model) {
        packageService.deletePackages(packageId);
        return "redirect:/packages";
    }
}