package com.food.cpg.purchaseorder;

import java.util.HashMap;
import java.util.Map;

public class PurchaseOrderStatusFactory {
    private static final Map<String, PurchaseOrderStatus> purchaseOrderStatuses = new HashMap<>();

    private static PurchaseOrderStatusFactory purchaseOrderStatusFactory;

    private PurchaseOrderStatusFactory() {
        purchaseOrderStatuses.put(PurchaseOrderStatus.Status.OPEN.name(), new PurchaseOpenOrderStatus());
        purchaseOrderStatuses.put(PurchaseOrderStatus.Status.PLACED.name(), new PurchasePlacedOrderStatus());
        purchaseOrderStatuses.put(PurchaseOrderStatus.Status.RECEIVED.name(), new PurchaseReceivedOrderStatus());
    }

    public static PurchaseOrderStatusFactory getInstance() {
        if (purchaseOrderStatusFactory == null) {
            purchaseOrderStatusFactory = new PurchaseOrderStatusFactory();
        }
        return purchaseOrderStatusFactory;
    }

    public PurchaseOrderStatus makeOrderStatus(String orderStatus) {
        return purchaseOrderStatuses.get(orderStatus);
    }
}
