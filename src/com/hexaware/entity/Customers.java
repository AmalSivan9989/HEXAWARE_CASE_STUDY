package com.hexaware.entity;

public class Customers {
    private int CustomerId;
    private String customerName;
    private String email;
    private String password;

    public Customers() {
    }

    public Customers(String customerName, String email, String password) {
        this.customerName = customerName;
        this.email = email;
        this.password = password;
    }

    public Customers(int customerId, String customerName, String email, String password) {
        CustomerId = customerId;
        this.customerName = customerName;
        this.email = email;
        this.password = password;
    }

    public int getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(int customerId) {
        CustomerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
