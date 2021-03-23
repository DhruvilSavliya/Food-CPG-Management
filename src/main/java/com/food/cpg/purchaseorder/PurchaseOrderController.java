package com.food.cpg.purchaseorder;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.food.cpg.models.Unit;
import com.food.cpg.rawmaterial.RawMaterial;
import com.food.cpg.vendor.Vendor;

@Controller
public class PurchaseOrderController {

    private static final String REDIRECT_NOTATION = "redirect:";
    private static final String PURCHASE_ORDERS_END_POINT = "/purchase-orders";
    private static final String SHOW_PURCHASE_ORDERS_ROUTE = "purchase-order/purchase-orders";
    private static final String SHOW_ADD_PURCHASE_ORDER_FORM_ROUTE = "purchase-order/add-purchase-order";
    private static final String VIEW_OPEN_PURCHASE_ORDERS_KEY = "openPurchaseOrders";
    private static final String VIEW_PLACED_PURCHASE_ORDERS_KEY = "placedPurchaseOrders";
    private static final String VIEW_RECEIVED_PURCHASE_ORDERS_KEY = "receivedPurchaseOrders";
    private static final String VIEW_RAW_MATERIALS_KEY = "rawMaterials";
    private static final String VIEW_UNITS_KEY = "units";
    private static final String VIEW_VENDORS_KEY = "vendors";
    private static final String SHOW_ADD_PURCHASE_ORDER_BYITEM_FORM_ROUTE = "purchase-order/add-purchase-order-byitem";

    @GetMapping("/purchase-orders")
    public String showPurchaseOrders(PurchaseOrder purchaseOrder,Model model) {
        List<PurchaseOrder> openPurchaseOrders = purchaseOrder.getOpenPurchaseOrder();
        model.addAttribute(VIEW_OPEN_PURCHASE_ORDERS_KEY, openPurchaseOrders);
        List<PurchaseOrder> placedPurchaseOrders = purchaseOrder.getPlacedPurchaseOrder();
        model.addAttribute(VIEW_PLACED_PURCHASE_ORDERS_KEY, placedPurchaseOrders);
        List<PurchaseOrder> receivedPurchaseOrders = purchaseOrder.getReceivedPurchaseOrder();
        model.addAttribute(VIEW_RECEIVED_PURCHASE_ORDERS_KEY, receivedPurchaseOrders);
        return SHOW_PURCHASE_ORDERS_ROUTE;
    }

    @GetMapping("/add-purchase-order")
    public String showAddPurchaseOrderForm(PurchaseOrder purchaseOrder, PurchaseOrderRawMaterial purchaseOrderRawMaterial, RawMaterial rawMaterial, Vendor vendor, Model model) {
        model.addAttribute(VIEW_UNITS_KEY, Unit.values());
        model.addAttribute(VIEW_VENDORS_KEY, vendor.getAll());
        model.addAttribute(VIEW_RAW_MATERIALS_KEY, rawMaterial.getAll());
        return SHOW_ADD_PURCHASE_ORDER_FORM_ROUTE;
    }

    @GetMapping("/add-purchase-order-byitem")
    public String showAddPurchaseOrderByItemForm(PurchaseOrder purchaseOrder, PurchaseOrderRawMaterial purchaseOrderRawMaterial, RawMaterial rawMaterial,Vendor vendor, Model model) {
        model.addAttribute(VIEW_UNITS_KEY, Unit.values());
       model.addAttribute(VIEW_VENDORS_KEY, vendor.getAll());
        return SHOW_ADD_PURCHASE_ORDER_BYITEM_FORM_ROUTE;
    }

    @PostMapping("/add-po-raw-material")
    public String addPurchaseOrderRawMaterial(PurchaseOrder purchaseOrder, PurchaseOrderRawMaterial purchaseOrderRawMaterial, RawMaterial rawMaterial, Vendor vendor, Model model) {
        purchaseOrder.addPurchaseOrderRawMaterials(purchaseOrderRawMaterial);
        model.addAttribute(VIEW_UNITS_KEY, Unit.values());
        model.addAttribute(VIEW_VENDORS_KEY, vendor.getAll());
        model.addAttribute(VIEW_RAW_MATERIALS_KEY, rawMaterial.getAll());
        return SHOW_ADD_PURCHASE_ORDER_FORM_ROUTE;
    }

    @PostMapping("/save-purchase-order")
    public String savePurchaseOrder(PurchaseOrder purchaseOrder) {
        purchaseOrder.save();
        return redirectToPurchaseOrders();
    }

    private String redirectToPurchaseOrders() {
        return REDIRECT_NOTATION + PURCHASE_ORDERS_END_POINT;
    }

    @GetMapping("/purchase-orders/delete/{purchaseOrderNumber}")
    public String deletePurchaseOrder(@PathVariable("purchaseOrderNumber") String purchaseOrderNumber,PurchaseOrder purchaseOrder,PurchaseOrderRawMaterial purchaseOrderRawMaterial) {
        purchaseOrder.setOrderNumber(purchaseOrderNumber);
        purchaseOrderRawMaterial.setPurchaseOrderNumber(purchaseOrderNumber);
        purchaseOrder.delete();
        purchaseOrderRawMaterial.delete();
        return redirectToPurchaseOrders();
    }

    @GetMapping("/purchase-orders/place/{purchaseOrderNumber}")
    public String placeOrder(@PathVariable("purchaseOrderNumber") String purchaseOrderNumber, PurchaseOrder purchaseOrder) {
        purchaseOrder.setOrderNumber(purchaseOrderNumber);
        purchaseOrder.moveOrderToPlaced();
        return redirectToPurchaseOrders();
    }

    @GetMapping("/purchase-orders/receive/{purchaseOrderNumber}")
    public String receiveOrder(@PathVariable("purchaseOrderNumber") String purchaseOrderNumber, PurchaseOrder purchaseOrder){
        purchaseOrder.setOrderNumber(purchaseOrderNumber);
        purchaseOrder.moveOrderToReceived();
        return redirectToPurchaseOrders();
    }
}