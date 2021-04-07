package com.food.cpg.analytics;

import com.food.cpg.authentication.AuthenticationSessionDetails;
import com.food.cpg.databasepersistence.PersistenceFactory;
import com.food.cpg.salesorder.ISalesOrderPersistence;
import com.food.cpg.salesorder.SalesOrder;

import java.util.List;

public class SalesPerformance {

    private String month;
    private int totalOrders;
    private double totalSales;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(int totalOrders) {
        this.totalOrders = totalOrders;
    }

    public double getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(double totalSales) {
        this.totalSales = totalSales;
    }

    public void generateSalesPerformance() {
        int loggedInManufacturerId = getLoggedInManufacturerId();

        List<SalesOrder> salesOrders = getSalesOrderPersistence().getAllPaidOrders(loggedInManufacturerId);

        int salesordernumber =0;
        double costofsalesorder = 0;
        for (SalesOrder salesOrder : salesOrders){
            salesordernumber++;
            costofsalesorder= costofsalesorder+salesOrder.getTotalCost();
        }
        this.setTotalOrders(salesordernumber);
        this.setTotalSales(costofsalesorder);

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

