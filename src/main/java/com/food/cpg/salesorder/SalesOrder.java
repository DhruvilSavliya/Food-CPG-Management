package com.food.cpg.salesorder;

import com.food.cpg.authentication.AuthenticationSessionDetails;
import com.food.cpg.databasepersistence.PersistenceFactory;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SalesOrder {

    private static final String SO_TIME_FORMAT = "ddMMHHmm";
    private static final String SO_PREFIX = "SO-";
    private String orderNumber;
    private SalesOrderStatus salesOrderStatus;
    private int manufacturerId;
    private int itemId;
    private int packageId;
    private double packageCost;
    private double shippingCost;
    private double tax;
    private double totalCost;
    private boolean isForCharity = false;
    private String buyerDetails;
    private Timestamp statusChangeDate;


    public SalesOrder() {
        String generatedOrderNumber = generateOrderNumber();
        setOrderNumber(generatedOrderNumber);
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(int manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public double getPackageCost() {
        return packageCost;
    }

    public void setPackageCost(double packageCost) {
        this.packageCost = packageCost;
    }

    public double getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(double shippingCost) {
        this.shippingCost = shippingCost;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public boolean getIsForCharity() {
        return isForCharity;
    }

    public void setIsForCharity(boolean isForCharity) {
        this.isForCharity = isForCharity;
    }

    public String getBuyerDetails() {
        return buyerDetails;
    }

    public void setBuyerDetails(String buyerDetails) {
        this.buyerDetails = buyerDetails;
    }

    public SalesOrderStatus getSalesOrderStatus() {
        return salesOrderStatus;
    }

    public void setSalesOrderStatus(SalesOrderStatus salesOrderStatus) {
        this.salesOrderStatus = salesOrderStatus;
    }

    public Timestamp getStatusChangeDate() {
        return statusChangeDate;
    }

    public void setStatusChangeDate(Timestamp statusChangeDate) {
        this.statusChangeDate = statusChangeDate;
    }

    public List<SalesOrder> getAllOpenOrders() {
        int loggedInManufacturerId = getLoggedInManufacturerId();
        return getPersistence().getAllOpenOrders(loggedInManufacturerId);
    }

    public List<SalesOrder> getAllPackagedOrders() {
        int loggedInManufacturerId = getLoggedInManufacturerId();
        return getPersistence().getAllPackagedOrders(loggedInManufacturerId);
    }

    public List<SalesOrder> getAllShippedOrders() {
        int loggedInManufacturerId = getLoggedInManufacturerId();
        return getPersistence().getAllShippedOrders(loggedInManufacturerId);
    }

    private String generateOrderNumber() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(SO_TIME_FORMAT);
        LocalDateTime currentDateTime = LocalDateTime.now();
        String formattedCurrentDateTime = dateTimeFormatter.format(currentDateTime);

        return SO_PREFIX + formattedCurrentDateTime;
    }

    public List<SalesOrder> getAllPaidOrders() {
        int loggedInManufacturerId = getLoggedInManufacturerId();
        return getPersistence().getAllPaidOrders(loggedInManufacturerId);
    }

    public void load() {
        getPersistence().load(this);
    }

    public void delete() {
        getPersistence().delete(this.getOrderNumber());
    }

    public void save() {
        int loggedInManufacturerId = getLoggedInManufacturerId();
        this.setManufacturerId(loggedInManufacturerId);
        getPersistence().save(this);
    }

    public void calculateTotalCost() {
        Double cost = 0.0;
        cost = this.getPackageCost();
        cost += this.getShippingCost();
        Double tax = this.getTax();
        cost += (cost * tax / 100);
        this.setTotalCost(cost);
    }

    public void moveOrderToNextStage() {
        this.getSalesOrderStatus().moveOrder(this.getOrderNumber());
    }

    private ISalesOrderPersistence getPersistence() {
        PersistenceFactory persistenceFactory = PersistenceFactory.getPersistenceFactory();
        return persistenceFactory.getSalesOrderPersistence();
    }

    private int getLoggedInManufacturerId() {
        AuthenticationSessionDetails authenticationSessionDetails = AuthenticationSessionDetails.getInstance();
        return authenticationSessionDetails.getAuthenticatedUserId();
    }
}
