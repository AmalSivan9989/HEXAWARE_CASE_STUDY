package com.hexaware.entity;

import com.hexaware.util.HexaConstants;

import java.time.LocalDate;

public class Orders {
    private int orderId;
    private int customerId;
    private LocalDate orderDate;
    private double totalPrice;
    private String shippingAddress;

    public Orders() {
    }

    public Orders(int orderId, int customerId, LocalDate orderDate, double totalPrice, String shippingAddress) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.shippingAddress = shippingAddress;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }


    @Override
    public String toString() {
        StringBuilder sb=new StringBuilder();
        sb.append(customerId);
        sb.append(HexaConstants.DELIMITER);
        sb.append(orderId);
        sb.append(HexaConstants.DELIMITER);
        sb.append(orderDate);
        sb.append(HexaConstants.DELIMITER);
        sb.append(totalPrice);
        sb.append(HexaConstants.DELIMITER);
        sb.append(shippingAddress);
        sb.append(HexaConstants.DELIMITER);

        return sb.toString();
    }



}
