package com.food.cpg.purchaseorder;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.food.cpg.models.Unit;
import com.food.cpg.rawmaterial.RawMaterial;
import com.food.cpg.vendor.Vendor;
import com.food.cpg.item.Item;

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
    private static final String VIEW_ITEMS_KEY = "items";
    private static final String SHOW_ADD_PURCHASE_ORDER_BYITEM_FORM_ROUTE = "purchase-order/add-purchase-order-byitem";

    @GetMapping("/purchase-orders")
    public String showPurchaseOrders(PurchaseOrder purchaseOrder, Model model) {
        model.addAttribute(VIEW_OPEN_PURCHASE_ORDERS_KEY, purchaseOrder.getAllOpenOrders());
        model.addAttribute(VIEW_PLACED_PURCHASE_ORDERS_KEY, purchaseOrder.getAllPlacedOrders());
        model.addAttribute(VIEW_RECEIVED_PURCHASE_ORDERS_KEY, purchaseOrder.getAllReceivedOrders());
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
    public String showAddPurchaseOrderByItemForm(PurchaseOrderByItem purchaseOrderByItem, PurchaseOrderRawMaterial purchaseOrderRawMaterial, Item item, Model model) {
        model.addAttribute(VIEW_ITEMS_KEY, item.getAll());
        return SHOW_ADD_PURCHASE_ORDER_BYITEM_FORM_ROUTE;
    }

    @PostMapping("/add-po-raw-material")
    public String addPurchaseOrderRawMaterial(PurchaseOrder purchaseOrder, PurchaseOrderRawMaterial purchaseOrderRawMaterial, RawMaterial rawMaterial, Vendor vendor, Model model) {
        purchaseOrderRawMaterial.loadCost(rawMaterial);
        purchaseOrder.addPurchaseOrderRawMaterials(purchaseOrderRawMaterial);

        model.addAttribute(VIEW_UNITS_KEY, Unit.values());
        model.addAttribute(VIEW_VENDORS_KEY, vendor.getAll());
        model.addAttribute(VIEW_RAW_MATERIALS_KEY, rawMaterial.getAll());
        return SHOW_ADD_PURCHASE_ORDER_FORM_ROUTE;
    }

    @PostMapping("/save-purchase-order-byitem")
    public String savePurchaseOrderByitem(PurchaseOrderByItem purchaseOrderByItem) {
        purchaseOrderByItem.addPurchaseOrderByItemRawMaterials();
        purchaseOrderByItem.createPurchaseOrderByItem();
        return redirectToPurchaseOrders();
    }

    @PostMapping("/save-purchase-order")
    public String savePurchaseOrder(PurchaseOrder purchaseOrder) {
        purchaseOrder.save();
        return redirectToPurchaseOrders();
    }

    private String redirectToPurchaseOrders() {
        return REDIRECT_NOTATION + PURCHASE_ORDERS_END_POINT;
    }

    @GetMapping("/purchase-orders/delete/{orderNumber}")
    public String deletePurchaseOrder(@PathVariable("orderNumber") String orderNumber, PurchaseOrder purchaseOrder, PurchaseOrderRawMaterial purchaseOrderRawMaterial) {
        purchaseOrder.setOrderNumber(orderNumber);
        purchaseOrderRawMaterial.setPurchaseOrderNumber(orderNumber);
        purchaseOrder.delete();
        purchaseOrderRawMaterial.delete();
        return redirectToPurchaseOrders();
    }

    @GetMapping("/purchase-orders/move/{orderNumber}")
    public String movePurchaseOrder(@PathVariable("orderNumber") String orderNumber, PurchaseOrder purchaseOrder) {
        purchaseOrder.setOrderNumber(orderNumber);
        purchaseOrder.load();
        purchaseOrder.moveOrderToNextStage();
        return redirectToPurchaseOrders();
    }


}