package com.food.cpg.salesorder;

import java.util.HashMap;
import java.util.Map;

public class SalesOrderStatusFactory {

    private static final Map<String, SalesOrderStatus> salesOrderStatuses = new HashMap<>();

    private static SalesOrderStatusFactory salesOrderStatusFactory;

    private SalesOrderStatusFactory() {
        salesOrderStatuses.put(SalesOrderStatus.Status.OPEN.name(), new SalesOpenOrderStatus());
        salesOrderStatuses.put(SalesOrderStatus.Status.PACKAGED.name(), new SalesPackagedOrderStatus());
        salesOrderStatuses.put(SalesOrderStatus.Status.SHIPPED.name(), new SalesShippedOrderStatus());
    }

    public static SalesOrderStatusFactory getInstance() {
        if (salesOrderStatusFactory == null) {
            salesOrderStatusFactory = new SalesOrderStatusFactory();
        }
        return salesOrderStatusFactory;
    }

    public SalesOrderStatus makeOrderStatus(String orderStatus) {
        return salesOrderStatuses.get(orderStatus);
    }
}
