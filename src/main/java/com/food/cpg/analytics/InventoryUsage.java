package com.food.cpg.analytics;

import com.food.cpg.authentication.AuthenticationSessionDetails;
import com.food.cpg.databasepersistence.PersistenceFactory;
import com.food.cpg.purchaseorder.PurchaseOrder;
import com.food.cpg.purchaseorder.PurchaseOrderByItem;
import com.food.cpg.purchaseorder.PurchaseOrderRawMaterial;
import com.food.cpg.rawmaterial.RawMaterial;
import com.food.cpg.salesorder.ISalesOrderPersistence;
import com.food.cpg.salesorder.SalesOrder;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static java.util.Calendar.DATE;

public class InventoryUsage {

    private static final int DAYS_BETWEEN_START_DATE_AND_TODAY = 7;
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    private int rawMaterialId;
    private String rawMaterialName;
    private double quantityForSales;
    private double quantityForCharity;
    private double totalQuantity;

    @DateTimeFormat(pattern = DATE_FORMAT)
    private Date StartDate;

    @DateTimeFormat(pattern = DATE_FORMAT)
    private Date EndDate;

    public InventoryUsage() {
        initializeInventory();
    }

    public int getRawMaterialId() {
        return rawMaterialId;
    }

    public void setRawMaterialId(int rawMaterialId) {
        this.rawMaterialId = rawMaterialId;
    }

    public String getRawMaterialName() {
        return rawMaterialName;
    }

    public void setRawMaterialName(String rawMaterialName) {
        this.rawMaterialName = rawMaterialName;
    }

    public double getQuantityForSales() {
        return quantityForSales;
    }

    public void setQuantityForSales(double quantityForSales) {
        this.quantityForSales = quantityForSales;
    }

    public double getQuantityForCharity() {
        return quantityForCharity;
    }

    public void setQuantityForCharity(double quantityForCharity) {
        this.quantityForCharity = quantityForCharity;
    }

    public double getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(double totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public Date getStartDate() {
        return StartDate;
    }

    public void setStartDate(Date startDate) {
        StartDate = startDate;
    }

    public Date getEndDate() {
        return EndDate;
    }

    public void setEndDate(Date endDate) {
        EndDate = endDate;
    }

    private void initializeInventory() {
        Date endDateValue = new Date();

        Calendar startDateValue = Calendar.getInstance();
        startDateValue.setTime(endDateValue);
        startDateValue.add(DATE, (DAYS_BETWEEN_START_DATE_AND_TODAY * -1));

        this.setStartDate(startDateValue.getTime());
        this.setEndDate(endDateValue);
    }

    public void generateInventoryUsage() {
        int loggedInManufacturerId = getLoggedInManufacturerId();
        PurchaseOrderByItem purchaseOrderByItem = new PurchaseOrderByItem();

        List<InventoryUsage> inventoryUsages = new ArrayList<>();
        List<PurchaseOrderRawMaterial> rawMaterialList;
        List<SalesOrder> salesOrders = getSalesOrderPersistence().getAllPaidOrders(loggedInManufacturerId);

        for (SalesOrder salesOrder : salesOrders) {
            int itemId = salesOrder.getItemId();
            rawMaterialList = purchaseOrderByItem.getPurchaseOrderItemRawMaterial(itemId);
            for (PurchaseOrderRawMaterial purchaseOrderRawMaterial : rawMaterialList) {
                for (InventoryUsage inventoryUsage : inventoryUsages){
                    if (purchaseOrderRawMaterial.getRawMaterialId() == inventoryUsage.getRawMaterialId() ){
                        if (salesOrder.getIsForCharity()) {
                            double updatedForCharityQuantity = inventoryUsage.getQuantityForCharity() + purchaseOrderRawMaterial.getRawMaterialQuantity();
                            inventoryUsage.setQuantityForCharity(updatedForCharityQuantity);
                        }
                        else{
                            double updatedForSalesQuantity = inventoryUsage.getQuantityForSales() + purchaseOrderRawMaterial.getRawMaterialQuantity();
                            inventoryUsage.setQuantityForSales(updatedForSalesQuantity);
                        }
                    }
                    else{
                        inventoryUsage = new InventoryUsage();
                        inventoryUsage.setRawMaterialId(purchaseOrderRawMaterial.getRawMaterialId());
                        inventoryUsage.setRawMaterialName(purchaseOrderRawMaterial.getRawMaterialName());
                        if (salesOrder.getIsForCharity()) {
                            double updatedForCharityQuantity = purchaseOrderRawMaterial.getRawMaterialQuantity();
                            inventoryUsage.setQuantityForCharity(updatedForCharityQuantity);
                        }
                        else{
                            double updatedForSalesQuantity = purchaseOrderRawMaterial.getRawMaterialQuantity();
                            inventoryUsage.setQuantityForSales(updatedForSalesQuantity);
                        }

                        inventoryUsages.add(inventoryUsage);
                    }

                }
        }
        }
    }

    public boolean isDateInRange(Date date) {
        if (date.compareTo(getStartDate()) >= 0 && date.compareTo(getEndDate()) <= 0) {
            return true;
        }
        return false;
    }

    private ISalesOrderPersistence getSalesOrderPersistence() {
        PersistenceFactory persistenceFactory = PersistenceFactory.getPersistenceFactory();
        return persistenceFactory.getSalesOrderPersistence();
    }

    private int getLoggedInManufacturerId() {
        AuthenticationSessionDetails authenticationSessionDetails = AuthenticationSessionDetails.getInstance();
        return authenticationSessionDetails.getAuthenticatedUserId();
    }

}
