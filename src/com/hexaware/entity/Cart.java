package com.hexaware.entity;

import com.hexaware.util.HexaConstants;

public class Cart {
    private int cartId;
    private int customerId;
    private int productId;
    private int quantity;

    public Cart() {
    }

    public Cart(int cartId, int customerId, int productId, int quantity) {
        this.cartId = cartId;
        this.customerId = customerId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
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
        sb.append(cartId);
        sb.append(HexaConstants.DELIMITER);
        sb.append(customerId);
        sb.append(HexaConstants.DELIMITER);
        sb.append(productId);
        sb.append(HexaConstants.DELIMITER);
        sb.append(quantity);
        sb.append(HexaConstants.DELIMITER);

        return sb.toString();
    }

}
