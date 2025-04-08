package com.hexaware.entity;

public class Products {
    private int productId;
    private String productName;
    private double price;
    private String productDescription;
    private int stockQuantity;

    public Products() {
    }

    public Products(String productName, double price, String productDescription, int stockQuantity) {
        this.productName = productName;
        this.price = price;
        this.productDescription = productDescription;
        this.stockQuantity = stockQuantity;
    }

    public Products(int productId, String productName, double price, String productDescription, int stockQuantity) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.productDescription = productDescription;
        this.stockQuantity = stockQuantity;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
}
