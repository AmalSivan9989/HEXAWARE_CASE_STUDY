package com.hexaware.service;

import com.hexaware.dao.OrderProcessorRepositoryImpl;
import com.hexaware.entity.Customers;
import com.hexaware.entity.Products;
import com.hexaware.exception.DbConnectionException;

public class ProductAddedService {
OrderProcessorRepositoryImpl orderRepo=new OrderProcessorRepositoryImpl();


    public ProductAddedService() throws DbConnectionException {
    }


    public boolean addToCart1(){

        Customers customer = new Customers();
        customer.setCustomerId(1);

        Products product = new Products();
        product.setProductId(1);
        return orderRepo.addToCart(customer,product,2);

    }

    public boolean addToCart2(){
        Customers customer = new Customers();
        customer.setCustomerId(99);

        Products product = new Products();
        product.setProductId(99);

        return orderRepo.addToCart(customer,product,99);
    }




}
