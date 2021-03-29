package com.food.cpg.salesorder;

import java.sql.Timestamp;

public class SalesOrder {
    private String orderNumber;
    private SalesOrderStatus salesOrderStatus;
    private int itemId;
    private int packageId;
    private double packageCost;
    private double shippingCost;
    private double tax;
    private double totalCost;
    private boolean isForCharity = false;
    private String buyerDetails;
    private Timestamp statusChangeDate;

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
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

    public boolean isForCharity() {
        return isForCharity;
    }

    public void setForCharity(boolean forCharity) {
        isForCharity = forCharity;
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
}
