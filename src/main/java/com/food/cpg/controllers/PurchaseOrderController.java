package com.food.cpg.controllers;


import com.food.cpg.models.PurchaseOrder;
import com.food.cpg.services.IPurchaseOrderService;
import com.food.cpg.services.impl.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
/**
 * @author P.M
 */

/**
 * yet to implement
 * update item and raw material data on order received
 * reorder to create new order of same ingridents
 * edit order
 * delete order
 */


@Controller
public class PurchaseOrderController {

    private final IPurchaseOrderService purchaseOrderService;

    @Autowired
    public PurchaseOrderController(PurchaseOrderService purchaseOrderService) {
        this.purchaseOrderService = purchaseOrderService;
    }

    @GetMapping("/purchase-orders")
    public String showPurchaseOrders(Model model) {
        List<PurchaseOrder> purchaseOrderList = purchaseOrderService.getPurchaseOrderList(1);
        List<PurchaseOrder> placedOrderList = purchaseOrderService.getPlacedOrderList(1);
        List<PurchaseOrder> receivedOrderList = purchaseOrderService.getReceivedOrderList(1);
        if (purchaseOrderList.size() > 0) {
            model.addAttribute("purchaseOrder", purchaseOrderList);
        }
        if (purchaseOrderList.size() > 0) {
            model.addAttribute("placedOrder", placedOrderList);
        }
        if (purchaseOrderList.size() > 0) {
            model.addAttribute("receivedOrder", receivedOrderList);
        }
        return "purchase-order/purchase-orders";
    }

    @GetMapping("/purchase-orders/place/{orderid}")
    public String placeOrder(@PathVariable("orderid") String orderid, Model model) {
        purchaseOrderService.placePurchaseOrder(orderid);
        return "redirect:/purchase-orders";
    }

    @GetMapping("/purchase-orders/receive/{orderid}")
    public String receiveOrder(@PathVariable("orderid") String orderid, Model model){
        purchaseOrderService.receivePurchaseOrder(orderid);
        return "redirect:/purchase-orders";
    }

    @GetMapping("/purchase-orders/delete/{orderid}")
    public String deleteOrder(@PathVariable("orderid") String orderid , Model model){
        purchaseOrderService.deletePurchaseOrder(orderid);
        return "redirect:/purchase-orders";
    }

    @GetMapping("/purchase-orders/reorder/{orderid}")
    public String reOrder(@PathVariable("orderid") String orderid , Model model){
        purchaseOrderService.reorderPurchaseOrder(orderid);
        return "redirect:/purchase-orders";
    }


}
