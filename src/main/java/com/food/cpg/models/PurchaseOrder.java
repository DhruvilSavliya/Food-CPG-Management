package com.food.cpg.models;

import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class PurchaseOrder {
    private String orderid;
    private double cost;
    private String status;
    private String date;

    private Map<String, String> errors = new HashMap<>();


    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    public boolean isValidPurchaseOrder() {
        errors = new HashMap<>();

        boolean isValid = true;

        if (StringUtils.isEmpty(this.getOrderid())) {
            errors.put("orderid", "Orderid is required.");
            isValid = false;
        }

        if (StringUtils.isEmpty(this.getCost())) {
            errors.put("cost", "Total Cost not calculated.");
            isValid = false;
        }
        if (StringUtils.isEmpty(this.getStatus())) {
            errors.put("status", "Order Status Unavailable.");
            isValid = false;
        }
        if (StringUtils.isEmpty(this.getDate())) {
            errors.put("date", "Order Date not available.");
            isValid = false;
        }


        return isValid;
    }
}

