package com.food.cpg.controllers;

import java.util.List;

import com.food.cpg.PurchaseOrder.OpenStatus;
import com.food.cpg.PurchaseOrder.PlacedStatus;
import com.food.cpg.PurchaseOrder.ReceivedStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.food.cpg.constants.ApplicationConstants;
import com.food.cpg.models.PurchaseOrder;
import com.food.cpg.models.PurchaseOrderRawMaterial;
import com.food.cpg.models.Unit;
import com.food.cpg.services.IPurchaseOrderService;
import com.food.cpg.services.IRawMaterialService;
import com.food.cpg.services.IVendorService;
import com.food.cpg.services.impl.PurchaseOrderService;
import com.food.cpg.services.impl.RawMaterialService;
import com.food.cpg.services.impl.VendorService;

@Controller
public class PurchaseOrderController {

    private final IPurchaseOrderService purchaseOrderService;
    private final IRawMaterialService rawMaterialService;
    private final IVendorService vendorService;
    private final OpenStatus openStatus;
    private final PlacedStatus placedStatus;
    //private final ReceivedStatus receivedStatus;

    @Autowired
    public PurchaseOrderController(PurchaseOrderService purchaseOrderService, RawMaterialService rawMaterialService, VendorService vendorService,PlacedStatus placedStatus,OpenStatus openStatus,ReceivedStatus receivedStatus) {
        this.purchaseOrderService = purchaseOrderService;
        this.rawMaterialService = rawMaterialService;
        this.vendorService = vendorService;
        this.placedStatus = placedStatus;
        this.openStatus = openStatus;
        //this.receivedStatus = receivedStatus;
    }

    @GetMapping("/purchase-orders")
    public String showPurchaseOrders(Model model) {
        List<PurchaseOrder> purchaseOrders = purchaseOrderService.getPurchaseOrder(1);
        List<PurchaseOrder> placedOrders = purchaseOrderService.getPlacedOrder(1);
        List<PurchaseOrder> receivedOrders = purchaseOrderService.getReceivedOrder(1);
        model.addAttribute("purchaseOrder", purchaseOrders);
        model.addAttribute("placedOrder", placedOrders);
        model.addAttribute("receivedOrder", receivedOrders);
        return "purchase-order/purchase-orders";
    }

    @GetMapping("/add-purchase-order")
    public String showAddPurchaseOrderForm(PurchaseOrder purchaseOrder, PurchaseOrderRawMaterial purchaseOrderRawMaterial, Model model) {
        model.addAttribute("units", Unit.values());
        model.addAttribute("vendors", vendorService.getVendorsList(1));
        model.addAttribute("rawMaterials", rawMaterialService.getRawMaterialsList(1));
        return "purchase-order/add-purchase-order";
    }

    @GetMapping("/add-purchase-order-by-item")
    public String showPurchaseOrderbyItemForm( Model model){
        return "purchase-order/add-purchase-order-by-item";
    }

    @PostMapping("/add-po-raw-material")
    public String addPurchaseOrderRawMaterial(PurchaseOrder purchaseOrder, PurchaseOrderRawMaterial purchaseOrderRawMaterial, Model model) {
        purchaseOrder.addPurchaseOrderRawMaterials(purchaseOrderRawMaterial);
        model.addAttribute("units", Unit.values());
        model.addAttribute("vendors", vendorService.getVendorsList(1));
        model.addAttribute("rawMaterials", rawMaterialService.getRawMaterialsList(1));
        return "purchase-order/add-purchase-order";
    }

    @PostMapping("/save-purchase-order")
    public String savePurchaseOrder(PurchaseOrder purchaseOrder, BindingResult result, Model model, @RequestParam(value = "action") String action) {
        if (ApplicationConstants.CANCEL.equals(action)) {
            return "redirect:/purchase-orders";
        }

        purchaseOrderService.save(purchaseOrder);
        return "redirect:/purchase-orders";
    }

    @GetMapping("/purchase-orders/delete/{purchaseOrderNumber}")
    public String deletePurchaseOrder(@PathVariable("purchaseOrderNumber") String purchaseOrderNumber, Model model) {
        purchaseOrderService.delete(purchaseOrderNumber);
        return "redirect:/purchase-orders";
    }

    @GetMapping("/purchase-orders/place/{purchaseOrderNumber}")
    public String placeOrder(@PathVariable("purchaseOrderNumber") String purchaseOrderNumber, Model model) {
        openStatus.moveOrder(purchaseOrderNumber);
        return "redirect:/purchase-orders";
    }

    @GetMapping("/purchase-orders/receive/{purchaseOrderNumber}")
    public String receiveOrder(@PathVariable("purchaseOrderNumber") String purchaseOrderNumber, Model model){
        placedStatus.moveOrder(purchaseOrderNumber);
       // receivedStatus.moveOrder(purchaseOrderNumber);
        return "redirect:/purchase-orders";
    }
}