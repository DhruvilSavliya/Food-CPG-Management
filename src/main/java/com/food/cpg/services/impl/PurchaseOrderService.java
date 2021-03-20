package com.food.cpg.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.food.cpg.dao.IPurchaseOrderDAO;
import com.food.cpg.dao.IPurchaseOrderRawMaterialDAO;
import com.food.cpg.dao.impl.PurchaseOrderDAOImpl;
import com.food.cpg.dao.impl.PurchaseOrderRawMaterialDAOImpl;
import com.food.cpg.models.PurchaseOrder;
import com.food.cpg.services.IPurchaseOrderService;

@Service
public class PurchaseOrderService implements IPurchaseOrderService {

    private final IPurchaseOrderDAO purchaseOrderDAO;
    private final IPurchaseOrderRawMaterialDAO purchaseOrderRawMaterialDAO;

    @Autowired
    public PurchaseOrderService(PurchaseOrderDAOImpl purchaseOrderDAO, PurchaseOrderRawMaterialDAOImpl purchaseOrderRawMaterialDAO) {
        this.purchaseOrderDAO = purchaseOrderDAO;
        this.purchaseOrderRawMaterialDAO = purchaseOrderRawMaterialDAO;
    }

    @Override
    public List<PurchaseOrder> getPurchaseOrder(int manufacturerId) {
        return purchaseOrderDAO.getPurchaseOrder(manufacturerId);
    }

    @Override
    public List<PurchaseOrder> getPlacedOrder(int manufacturerId){
        return purchaseOrderDAO.getPlacedOrder(manufacturerId);
    }

    @Override
    public List<PurchaseOrder> getReceivedOrder(int manufacturerId){
        return purchaseOrderDAO.getReceivedOrder(manufacturerId);
    }


    @Override
    public void save(PurchaseOrder purchaseOrder) {
        purchaseOrderDAO.save(purchaseOrder);
        purchaseOrderRawMaterialDAO.save(purchaseOrder.getPurchaseOrderRawMaterials());
    }

    @Override
    public void delete(String purchaseOrderNumber) {
        purchaseOrderDAO.delete(purchaseOrderNumber);
        purchaseOrderRawMaterialDAO.delete(purchaseOrderNumber);


    }
}
