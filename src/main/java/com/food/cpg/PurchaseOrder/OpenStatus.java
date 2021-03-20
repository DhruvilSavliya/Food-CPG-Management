package com.food.cpg.PurchaseOrder;

import com.food.cpg.dao.IPurchaseOrderDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OpenStatus extends PurchaseOrderStatus{
    private final IPurchaseOrderDAO purchaseOrderDAO;

    @Autowired
    public OpenStatus(IPurchaseOrderDAO purchaseOrderDAO)
    {
        orderStatus="Open";
        this.purchaseOrderDAO = purchaseOrderDAO;
    }

    @Override
    public void moveOrder(String purchaseOrderNumber) {
        purchaseOrderDAO.moveToPlacedOrder(purchaseOrderNumber);

    }

}
