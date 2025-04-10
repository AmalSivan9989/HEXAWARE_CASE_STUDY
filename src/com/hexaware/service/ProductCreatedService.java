package com.hexaware.service;

import com.hexaware.dao.OrderProcessorRepositoryImpl;
import com.hexaware.entity.Products;
import com.hexaware.exception.DbConnectionException;

public class ProductCreatedService {
    OrderProcessorRepositoryImpl orderRepo=new OrderProcessorRepositoryImpl();

    public ProductCreatedService() throws DbConnectionException {
    }


    public boolean testProductCreation1(){
        Products p = new Products(10, "Keyboard",1999.00, "Mechanical keyboard", 20);
        return orderRepo.createProduct(p);
    }

    public boolean testProductCreation2(){
        Products p = new Products();
        p.setProductId(10);
        p.setPrice(1000);
        p.setProductDescription("good product");
        p.setStockQuantity(2);

        return orderRepo.createProduct(p);
    }
}
