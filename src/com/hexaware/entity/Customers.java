package com.hexaware.entity;

import com.hexaware.util.HexaConstants;

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


    @Override
    public String toString() {
        StringBuilder sb=new StringBuilder();
        sb.append(CustomerId);
        sb.append(HexaConstants.DELIMITER);
        sb.append(customerName);
        sb.append(HexaConstants.DELIMITER);
        sb.append(email);
        sb.append(HexaConstants.DELIMITER);
        sb.append(password);
        sb.append(HexaConstants.DELIMITER);

        return sb.toString();
    }



}
