package com.hexaware.dao;

import com.hexaware.entity.Customers;
import com.hexaware.entity.Products;

import java.util.List;
import java.util.Map;

public interface OrderProcessorRepository {
    public boolean createProduct(Products product);
    public boolean createCustomer(Customers customer);
    public boolean deleteProduct(int productId);
    public boolean deleteCustomer(int customerId);
    public boolean addToCart(Customers customer,Products product,int quantity);
    public boolean removeFromCart(Customers customer,Products product);
    public List<Products> getAllFromCart(Customers customer);
    public boolean placeOrder(Customers customer, List<Map<Products,Integer>> productQuantityList, String shippingAddress);
    public List<Map<Products,Integer>>getOrdersByCustomer(int customerId);


}
