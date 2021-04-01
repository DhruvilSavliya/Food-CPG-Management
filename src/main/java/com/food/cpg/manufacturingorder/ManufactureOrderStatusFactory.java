package com.food.cpg.manufacturingorder;

import java.util.HashMap;
import java.util.Map;

public class ManufactureOrderStatusFactory {

    private static final Map<String, ManufactureOrderStatus> manufactureOrderStatuses = new HashMap<>();

    private static ManufactureOrderStatusFactory manufactureOrderStatusFactory;

    private ManufactureOrderStatusFactory() {
        manufactureOrderStatuses.put(ManufactureOrderStatus.Status.OPEN.name(), new ManufactureOpenOrderStatus());
        manufactureOrderStatuses.put(ManufactureOrderStatus.Status.MANUFACTURED.name(), new ManufactureManufacturedOrderStatus());
        manufactureOrderStatuses.put(ManufactureOrderStatus.Status.PACKAGED.name(), new ManufacturePackagedOrderStatus());
    }

    public static ManufactureOrderStatusFactory getInstance() {
        if (manufactureOrderStatusFactory == null) {
            manufactureOrderStatusFactory = new ManufactureOrderStatusFactory();
        }
        return manufactureOrderStatusFactory;
    }

    public ManufactureOrderStatus makeOrderStatus(String orderStatus) {
        return manufactureOrderStatuses.get(orderStatus);
    }

}
