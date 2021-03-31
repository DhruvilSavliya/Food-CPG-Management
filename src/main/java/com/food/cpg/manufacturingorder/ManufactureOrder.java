package com.food.cpg.manufacturingorder;

import com.food.cpg.authentication.AuthenticationSessionDetails;
import com.food.cpg.databasepersistence.PersistenceFactory;
import com.food.cpg.item.Item;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ManufactureOrder {

    private static final String MO_TIME_FORMAT = "ddMMHHmm";
    private static final String MO_PREFIX = "MO-";

    private String orderNumber;
    private Integer manufacturerId;
    private Integer itemId;
    private Double itemQuantity;
    private String itemQuantityUOM;
    private String orderStatus;
    private Timestamp orderCreationDate;
    private Timestamp orderManufacturedDate;
    private Timestamp orderPackedDate;
    private Double manufacturingCost;
    private Double tax;
    private Double cost;
    private ManufactureOrderStatus manufactureOrderStatus;
    private Timestamp statusChangeDate;

    public ManufactureOrder() {
        String generatedOrderNumber = generateOrderNumber();
        setOrderNumber(generatedOrderNumber);
    }

    public static String getMoTimeFormat() {
        return MO_TIME_FORMAT;
    }

    public static String getMoPrefix() {
        return MO_PREFIX;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Integer getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(Integer manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Double getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(Double itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getItemQuantityUOM() {
        return itemQuantityUOM;
    }

    public void setItemQuantityUOM(String itemQuantityUOM) {
        this.itemQuantityUOM = itemQuantityUOM;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Timestamp getOrderCreationDate() {
        return orderCreationDate;
    }

    public void setOrderCreationDate(Timestamp orderCreationDate) {
        this.orderCreationDate = orderCreationDate;
    }

    public Timestamp getOrderManufacturedDate() {
        return orderManufacturedDate;
    }

    public void setOrderManufacturedDate(Timestamp orderManufacturedDate) {
        this.orderManufacturedDate = orderManufacturedDate;
    }

    public Timestamp getOrderPackedDate() {
        return orderPackedDate;
    }

    public void setOrderPackedDate(Timestamp orderPackedDate) {
        this.orderPackedDate = orderPackedDate;
    }

    public Double getManufacturingCost() {
        return manufacturingCost;
    }

    public void setManufacturingCost(Double manufacturingCost) {
        this.manufacturingCost = manufacturingCost;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public ManufactureOrderStatus getManufactureOrderStatus() {
        return manufactureOrderStatus;
    }

    public void setManufactureOrderStatus(ManufactureOrderStatus manufactureOrderStatus) {
        this.manufactureOrderStatus = manufactureOrderStatus;
    }

    public Timestamp getStatusChangeDate() {
        return statusChangeDate;
    }

    public void setStatusChangeDate(Timestamp statusChangeDate) {
        this.statusChangeDate = statusChangeDate;
    }

    private String generateOrderNumber() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(MO_TIME_FORMAT);
        LocalDateTime currentDateTime = LocalDateTime.now();
        String formattedCurrentDateTime = dateTimeFormatter.format(currentDateTime);

        return MO_PREFIX + formattedCurrentDateTime;
    }

    public List<ManufactureOrder> getAllOpenOrders() {
        int loggedInManufacturerId = getLoggedInManufacturerId();
        return getPersistence().getAllOpenOrders(loggedInManufacturerId);
    }

    public List<ManufactureOrder> getAllManufacturedOrders() {
        int loggedInManufacturerId = getLoggedInManufacturerId();
        return getPersistence().getAllManufacturedOrders(loggedInManufacturerId);
    }

    public List<ManufactureOrder> getAllPackagedOrders() {
        int loggedInManufacturerId = getLoggedInManufacturerId();
        return getPersistence().getAllPackagedOrders(loggedInManufacturerId);
    }

    public void load(){
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
        Double totalCost = 0.0;
        totalCost = getPersistence().loadItemCost(this.itemId);
        this.setCost(totalCost);
        Double costForAllQauntities = this.getCost() * this.getItemQuantity();
        totalCost = costForAllQauntities;
        totalCost += this.getManufacturingCost();
        Double tax = this.getTax();
        totalCost += (totalCost * tax/100);
        this.setCost(totalCost);
    }

    public void moveOrderToNextStage() {
        this.getManufactureOrderStatus().moveOrder(this.getOrderNumber());
    }


    private IManufactureOrderPersistence getPersistence() {
        PersistenceFactory persistenceFactory = PersistenceFactory.getPersistenceFactory();
        return persistenceFactory.getManufactureOrderPersistence();
    }

    private int getLoggedInManufacturerId() {
        AuthenticationSessionDetails authenticationSessionDetails = AuthenticationSessionDetails.getInstance();
        return authenticationSessionDetails.getAuthenticatedUserId();
    }
}
