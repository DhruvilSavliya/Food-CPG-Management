package com.food.cpg.analytics;

import com.food.cpg.authentication.AuthenticationSessionDetails;
import com.food.cpg.databasepersistence.PersistenceFactory;
import com.food.cpg.manufacturingorder.IManufactureOrderPersistence;
import com.food.cpg.manufacturingorder.ManufactureOrder;
import com.food.cpg.purchaseorder.IPurchaseOrderPersistence;
import com.food.cpg.purchaseorder.PurchaseOrder;
import com.food.cpg.salesorder.ISalesOrderPersistence;
import com.food.cpg.salesorder.SalesOrder;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static java.util.Calendar.DATE;

public class OrderStatistics {

    private static final int DAYS_BETWEEN_START_DATE_AND_TODAY = 7;
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    private Integer totalOrders;
    private Integer totalOpenPurchaseOrders;
    private Integer totalPlacedPurchaseOrders;
    private Integer totalReceivedPurchaseOrders;
    private Integer totalOpenManufactureOrders;
    private Integer totalManufacturedManufactureOrders;
    private Integer totalPackagedManufactureOrders;
    private Integer totalOpenSalesOrder;
    private Integer totalPackagedSalesOrder;
    private Integer totalShippedSalesOrder;
    private Integer totalPaidSalesOrder;
    private String mostOrderedRawMaterial;
    private String mostShippedItem;

    @DateTimeFormat(pattern = DATE_FORMAT)
    private Date startDate;

    @DateTimeFormat(pattern = DATE_FORMAT)
    private Date endDate;

    public OrderStatistics() {
        initializeOrderStatistics();
    }


    public Integer getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(Integer totalOrders) {
        this.totalOrders = totalOrders;
    }

    public Integer getTotalOpenPurchaseOrders() {
        return totalOpenPurchaseOrders;
    }

    public void setTotalOpenPurchaseOrders(Integer totalOpenPurchaseOrders) {
        this.totalOpenPurchaseOrders = totalOpenPurchaseOrders;
    }

    public Integer getTotalPlacedPurchaseOrders() {
        return totalPlacedPurchaseOrders;
    }

    public void setTotalPlacedPurchaseOrders(Integer totalPlacedPurchaseOrders) {
        this.totalPlacedPurchaseOrders = totalPlacedPurchaseOrders;
    }

    public Integer getTotalReceivedPurchaseOrders() {
        return totalReceivedPurchaseOrders;
    }

    public void setTotalReceivedPurchaseOrders(Integer totalReceivedPurchaseOrders) {
        this.totalReceivedPurchaseOrders = totalReceivedPurchaseOrders;
    }

    public Integer getTotalOpenManufactureOrders() {
        return totalOpenManufactureOrders;
    }

    public void setTotalOpenManufactureOrders(Integer totalOpenManufactureOrders) {
        this.totalOpenManufactureOrders = totalOpenManufactureOrders;
    }

    public Integer getTotalManufacturedManufactureOrders() {
        return totalManufacturedManufactureOrders;
    }

    public void setTotalManufacturedManufactureOrders(Integer totalManufacturedManufactureOrders) {
        this.totalManufacturedManufactureOrders = totalManufacturedManufactureOrders;
    }

    public Integer getTotalPackagedManufactureOrders() {
        return totalPackagedManufactureOrders;
    }

    public void setTotalPackagedManufactureOrders(Integer totalPackagedManufactureOrders) {
        this.totalPackagedManufactureOrders = totalPackagedManufactureOrders;
    }

    public Integer getTotalOpenSalesOrder() {
        return totalOpenSalesOrder;
    }

    public void setTotalOpenSalesOrder(Integer totalOpenSalesOrder) {
        this.totalOpenSalesOrder = totalOpenSalesOrder;
    }

    public Integer getTotalPackagedSalesOrder() {
        return totalPackagedSalesOrder;
    }

    public void setTotalPackagedSalesOrder(Integer totalPackagedSalesOrder) {
        this.totalPackagedSalesOrder = totalPackagedSalesOrder;
    }

    public Integer getTotalShippedSalesOrder() {
        return totalShippedSalesOrder;
    }

    public void setTotalShippedSalesOrder(Integer totalShippedSalesOrder) {
        this.totalShippedSalesOrder = totalShippedSalesOrder;
    }

    public Integer getTotalPaidSalesOrder() {
        return totalPaidSalesOrder;
    }

    public void setTotalPaidSalesOrder(Integer totalPaidSalesOrder) {
        this.totalPaidSalesOrder = totalPaidSalesOrder;
    }

    public String getMostOrderedRawMaterial() {
        return mostOrderedRawMaterial;
    }

    public void setMostOrderedRawMaterial(String mostOrderedRawMaterial) {
        this.mostOrderedRawMaterial = mostOrderedRawMaterial;
    }

    public String getMostShippedItem() {
        return mostShippedItem;
    }

    public void setMostShippedItem(String mostShippedItem) {
        this.mostShippedItem = mostShippedItem;
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

    public void initializeOrderStatistics() {
        Date endDateValue = new Date();

        Calendar startDateValue = Calendar.getInstance();
        startDateValue.setTime(endDateValue);
        startDateValue.add(DATE, (DAYS_BETWEEN_START_DATE_AND_TODAY * -1));

        this.setStartDate(startDateValue.getTime());
        this.setEndDate(endDateValue);
    }

    public void generateOrderStatistics() {
        int loggedInManufacturerId = getLoggedInManufacturerId();

        List<PurchaseOrder> openPurchaseOrders = getPurchaseOrderPersistence().getOpenPurchaseOrder(loggedInManufacturerId);
        List<PurchaseOrder> placedPurchaseOrders = getPurchaseOrderPersistence().getPlacedPurchaseOrder(loggedInManufacturerId);
        List<PurchaseOrder> receivedPurchaseOrders = getPurchaseOrderPersistence().getReceivedPurchaseOrder(loggedInManufacturerId);

        List<ManufactureOrder> openManufactureOrders = getManufactureOrderPersistence().getAllOpenOrders(loggedInManufacturerId);
        List<ManufactureOrder> manufacturedManufactureOrders = getManufactureOrderPersistence().getAllManufacturedOrders(loggedInManufacturerId);
        List<ManufactureOrder> packagedManufactureOrders = getManufactureOrderPersistence().getAllPackagedOrders(loggedInManufacturerId);

        List<SalesOrder> openSalesOrders = getSalesOrderPersistence().getAllOpenOrders(loggedInManufacturerId);
        List<SalesOrder> packagedSalesOrders = getSalesOrderPersistence().getAllPackagedOrders(loggedInManufacturerId);
        List<SalesOrder> shippedSalesOrders = getSalesOrderPersistence().getAllShippedOrders(loggedInManufacturerId);
        List<SalesOrder> paidSalesOrders = getSalesOrderPersistence().getAllPaidOrders(loggedInManufacturerId);

    }

    private IManufactureOrderPersistence getManufactureOrderPersistence(){
        PersistenceFactory persistenceFactory = PersistenceFactory.getPersistenceFactory();
        return persistenceFactory.getManufactureOrderPersistence();
    }

    private IPurchaseOrderPersistence getPurchaseOrderPersistence() {
        PersistenceFactory persistenceFactory = PersistenceFactory.getPersistenceFactory();
        return persistenceFactory.getPurchaseOrderPersistence();
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
