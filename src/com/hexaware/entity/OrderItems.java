package com.hexaware.entity;

import com.hexaware.util.HexaConstants;

public class OrderItems {
    private int orderItemId;
    private Orders orderId;
    private Products productId;
    private int quantity;

    public OrderItems() {
    }

    public OrderItems(int orderItemId, Orders orderId, Products productId, int quantity) {
        this.orderItemId = orderItemId;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public Orders getOrderId() {
        return orderId;
    }

    public void setOrderId(Orders orderId) {
        this.orderId = orderId;
    }

    public int getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(int orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Products getProductId() {
        return productId;
    }

    public void setProductId(Products productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        StringBuilder sb=new StringBuilder();
        sb.append(orderId);
        sb.append(HexaConstants.DELIMITER);
        sb.append(orderItemId);
        sb.append(HexaConstants.DELIMITER);
        sb.append(productId);
        sb.append(HexaConstants.DELIMITER);
        sb.append(quantity);
        sb.append(HexaConstants.DELIMITER);

        return sb.toString();

    }
}
