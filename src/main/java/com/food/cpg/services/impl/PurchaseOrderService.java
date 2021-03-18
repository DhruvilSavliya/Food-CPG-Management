package com.food.cpg.services.impl;

import com.food.cpg.dao.IPurchaseOrderDAO;
import com.food.cpg.dao.impl.PurchaseOrderDAOImpl;
import com.food.cpg.models.PurchaseOrder;
import com.food.cpg.services.IPurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * @author P.M
 */


@Service
public class PurchaseOrderService implements IPurchaseOrderService {

    private final IPurchaseOrderDAO purchaseOrderDAO;

    @Autowired
    public PurchaseOrderService(PurchaseOrderDAOImpl purchaseOrderDAO) {
        this.purchaseOrderDAO = purchaseOrderDAO;
    }

    @Override
    public List<PurchaseOrder> getPurchaseOrderList(int manufacturerId) {
        return purchaseOrderDAO.getPurchaseOrderList(manufacturerId);
    }

    @Override
    public List<PurchaseOrder> getPlacedOrderList(int manufacturerId) {
        return purchaseOrderDAO.getPlacedOrderList(manufacturerId);
    }

    @Override
    public List<PurchaseOrder> getReceivedOrderList(int manufacturerId) {
        return purchaseOrderDAO.getReceivedOrderList(manufacturerId);
    }
    @Override
    public void placePurchaseOrder(String orderid) {
        purchaseOrderDAO.placePurchaseOrder(orderid);
    }
    @Override
    public void receivePurchaseOrder(String orderid){
        purchaseOrderDAO.receivePurchaseOrder(orderid);
    }
    @Override
    public void deletePurchaseOrder(String orderid){
        purchaseOrderDAO.deletePurchaseOrder(orderid);
    }
    @Override
    public  void reorderPurchaseOrder(String orderid){
        purchaseOrderDAO.reorderPurchaseOrder(orderid);
    }

}
