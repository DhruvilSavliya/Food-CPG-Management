package com.food.cpg.PurchaseOrder;

import com.food.cpg.dao.IPurchaseOrderDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlacedStatus extends PurchaseOrderStatus{

    private final IPurchaseOrderDAO purchaseOrderDAO;

    @Autowired
    public PlacedStatus(IPurchaseOrderDAO purchaseOrderDAO)
    {
        orderStatus="Placed";
        this.purchaseOrderDAO = purchaseOrderDAO;
    }

    @Override
    public void moveOrder(String purchaseOrderNumber) {
        purchaseOrderDAO.moveToReceivedOrder(purchaseOrderNumber);

    }
}
