package com.hexaware.service;

import com.hexaware.dao.OrderProcessorRepositoryImpl;
import com.hexaware.entity.Customers;
import com.hexaware.entity.Products;
import com.hexaware.exception.CustomerNotFoundException;
import com.hexaware.exception.DbConnectionException;
import com.hexaware.exception.ProductNotFoundException;

public class ExceptionService {
    OrderProcessorRepositoryImpl orderRepo=new OrderProcessorRepositoryImpl();

    public ExceptionService() throws DbConnectionException {
    }

    public void addToCart1(Customers customer, Products product, int quantity) throws CustomerNotFoundException, ProductNotFoundException {
        if (customer == null) {
            throw new CustomerNotFoundException("Customer not found or is null.");
        }
        if (product == null) {
            throw new ProductNotFoundException("Product not found or is null.");
        }
        boolean add = orderRepo.addToCart(customer, product, quantity);
        if (!add) {
            throw new CustomerNotFoundException("Customer not added yet!");
  }



    }
}
