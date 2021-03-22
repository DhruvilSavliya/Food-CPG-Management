package com.food.cpg.purchaseorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public class ReceivedStatus extends PurchaseOrderStatus  {

    private static final String RECEIVED_STATUS ="Received";
    public void setOrderStatus()
    {
        this.orderStatus = RECEIVED_STATUS;
    }
    public void moveOrder(String purchaseOrderNumber) {

    }


}
