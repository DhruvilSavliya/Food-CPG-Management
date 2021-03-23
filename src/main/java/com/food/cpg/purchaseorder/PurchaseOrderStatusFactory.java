package com.food.cpg.purchaseorder;

public class PurchaseOrderStatusFactory {
    public static PurchaseOrderStatus getPurchaseOrderStatus(String orderStatus) {
        PurchaseOrderStatus result;
        switch(orderStatus){
                    case "Open":
                        result = new OpenStatus() ;
                        break;
                    case"Placed":
                        result = new PlacedStatus();
                        break;
                    case "Received":
                        result = new ReceivedStatus();
                        break;
            default:
                throw new IllegalStateException("Unexpected value: " + orderStatus);
        }

        return result;
    }
}
 