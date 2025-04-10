package com.hexaware.service;

import com.hexaware.dao.OrderProcessorRepositoryImpl;
import com.hexaware.entity.Customers;
import com.hexaware.entity.Products;
import com.hexaware.exception.DbConnectionException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductOrderService {
    OrderProcessorRepositoryImpl orderService=new OrderProcessorRepositoryImpl();

    public ProductOrderService() throws DbConnectionException {
    }


    public boolean testPlaceOrder1(){

        Customers customer = new Customers();
        customer.setCustomerId(1);

        Products product = new Products();
        product.setProductId(1);

        Map<Products,Integer> productMap=new HashMap<>();
        productMap.put(product,1);
        List<Map<Products, Integer>> productList=new ArrayList<>();
        productList.add(productMap);

        return orderService.placeOrder(customer,productList,"chennai");
    }


    public boolean testPlaceOrder2(){

        Customers customer = new Customers();
        customer.setCustomerId(10);

        Products product = new Products();
        product.setProductId(10);

        Map<Products,Integer> productMap=new HashMap<>();
        productMap.put(product,1);
        List<Map<Products, Integer>> productList=new ArrayList<>();
        productList.add(productMap);

        return orderService.placeOrder(customer,productList,"chennai");
    }
}
