package com.food.cpg.analytics;

import com.food.cpg.authentication.AuthenticationSessionDetails;
import com.food.cpg.databasepersistence.PersistenceFactory;
import com.food.cpg.item.Item;
import com.food.cpg.item.ItemRawMaterial;
import com.food.cpg.packaging.Packages;
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

    private String rawMaterialName;
    private double quantityForSales;
    private double quantityForCharity;
    private double totalQuantity;

    @DateTimeFormat(pattern = DATE_FORMAT)
    private Date startDate;

    @DateTimeFormat(pattern = DATE_FORMAT)
    private Date endDate;

    public InventoryUsage() {
        initializeInventory();
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
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }


    public void initializeInventory() {
        Date endDateValue = new Date();

        Calendar startDateValue = Calendar.getInstance();
        startDateValue.setTime(endDateValue);
        startDateValue.add(DATE, (DAYS_BETWEEN_START_DATE_AND_TODAY * -1));

        this.setStartDate(startDateValue.getTime());
        this.setEndDate(endDateValue);
    }

    public void generateInventoryUsage() {
        int loggedInManufacturerId = getLoggedInManufacturerId();
        Packages packages = new Packages();
        Item item = new Item();
        RawMaterial rawMaterial = new RawMaterial();
        PurchaseOrderByItem purchaseOrderByItem = new PurchaseOrderByItem();
        List<SalesOrder> salesOrders = getSalesOrderPersistence().getAllPaidOrders(loggedInManufacturerId);
        List<InventoryUsage> inventoryUsages = new ArrayList<>();
        int size = salesOrders.size();


        for (SalesOrder salesOrder : salesOrders) {
            if (isDateInRange(salesOrder.getStatusChangeDate())) {
                int packageid = salesOrder.getPackageId();
                packages.setPackageId(packageid);
                packages.load();
                int itemid = packages.getItemId();
                double itemquantity = packages.getQuantity();
                item.setId(itemid);
                item.load();

                List<PurchaseOrderRawMaterial> itemRawMaterials = purchaseOrderByItem.getPurchaseOrderItemRawMaterial(itemid);
                for (PurchaseOrderRawMaterial purchaseOrderRawMaterial : itemRawMaterials) {
                    int rawmaterialid = purchaseOrderRawMaterial.getRawMaterialId();
                    double rawmaterialquantity = purchaseOrderRawMaterial.getRawMaterialQuantity();
                    double totalrawmaterialquantity = rawmaterialquantity * itemquantity;
                    rawMaterial.setId(rawmaterialid);
                    rawMaterial.load();
                    String rawmaterialname = rawMaterial.getName();

                    for (InventoryUsage inventoryusage : inventoryUsages) {
                        System.out.println(inventoryusage.getRawMaterialName());
                        if (rawmaterialname.equals(inventoryusage.getRawMaterialName())) {
                            if (salesOrder.getIsForCharity()) {
                                double updatedForCharityQuantity = inventoryusage.getQuantityForCharity() + totalrawmaterialquantity;
                                inventoryusage.setQuantityForCharity(updatedForCharityQuantity);
                            } else {
                                double updatedForSalesQuantity = inventoryusage.getQuantityForSales() + totalrawmaterialquantity;
                                inventoryusage.setQuantityForSales(updatedForSalesQuantity);
                            }
                            inventoryusage.setTotalQuantity(inventoryusage.getQuantityForCharity() + inventoryusage.getQuantityForSales());
                        }
                        else {
                            InventoryUsage inventoryUsage = new InventoryUsage();
                            inventoryUsage.setRawMaterialName(purchaseOrderRawMaterial.getRawMaterialName());
                            if (salesOrder.getIsForCharity()) {
                                inventoryUsage.setQuantityForCharity(totalrawmaterialquantity);
                                System.out.println(inventoryusage.getRawMaterialName()+"new");
                            } else {
                                inventoryUsage.setQuantityForSales(totalrawmaterialquantity);
                            }
                            inventoryusage.setTotalQuantity(inventoryusage.getQuantityForCharity() + inventoryusage.getQuantityForSales());
                            inventoryUsages.add(inventoryUsage);
                        }

                    }
                }
            }
        }System.out.println(inventoryUsages.size()+"size");
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
