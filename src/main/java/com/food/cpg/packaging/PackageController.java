package com.food.cpg.packaging;

import com.food.cpg.item.Item;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class PackageController {
    private static final String REDIRECT_NOTATION = "redirect:";
    private static final String PACKAGES_END_POINT = "/packages";
    private static final String SHOW_PACKAGES_ROUTE = "packages/packages";
    private static final String SHOW_ADD_PACKAGES_FORM_ROUTE = "packages/add-packages";
    private static final String SHOW_EDIT_PACKAGES_FORM_ROUTE = "packages/edit-packages";
    private static final String VIEW_PACKAGE_KEY = "package";
    private static final String VIEW_PACKAGES_KEY = "packages";
    private static final String VIEW_ITEM_KEY = "item";

    @GetMapping("/packages")
    public String showPackages(Package packages, Model model) {
        List<Package> packagesList = packages.getAll();
        model.addAttribute(VIEW_PACKAGES_KEY, packagesList);
        return SHOW_PACKAGES_ROUTE;
    }

    @GetMapping("/add-packages")
    public String showAddPackages(Package packages, Item item, Model model) {
        model.addAttribute(VIEW_ITEM_KEY, item.getAll());
        return SHOW_ADD_PACKAGES_FORM_ROUTE;
    }

    @PostMapping("/save-packages")
    public String savePackages(Package packages, Item item, BindingResult result, Model model) {
        if (result.hasErrors() || !packages.isValidPackage()) {
            model.addAttribute(VIEW_ITEM_KEY, item.getAll());
            return SHOW_ADD_PACKAGES_FORM_ROUTE;
        }

        packages.save();
        return redirectToPackagesList();
    }

    @PostMapping("/save-packages/{packageId}")
    public String editPackages(Package packages, BindingResult result) {
        if (result.hasErrors() || !packages.isValidPackage()) {
            return SHOW_EDIT_PACKAGES_FORM_ROUTE;
        }

        packages.update();
        return redirectToPackagesList();
    }

    @GetMapping("/packages/edit/{packageId}")
    public String showEditPackagesForm(@PathVariable("packageId") int packageId, Package packages, Item item, Model model) {
        packages.setPackageId(packageId);
        packages.load();
        model.addAttribute(VIEW_PACKAGE_KEY, packages);
        model.addAttribute(VIEW_ITEM_KEY, item.getAll());
        return SHOW_EDIT_PACKAGES_FORM_ROUTE;
    }

    @GetMapping("/packages/delete/{packageId}")
    public String deletePackags(@PathVariable("packageId") int packageId, Package packages) {
        packages.setPackageId(packageId);
        packages.delete();

        return redirectToPackagesList();
    }

    private String redirectToPackagesList() {

        return REDIRECT_NOTATION + PACKAGES_END_POINT;
    }
}