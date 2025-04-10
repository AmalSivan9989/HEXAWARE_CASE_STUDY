package com.hexaware.test;

import com.hexaware.dao.OrderProcessorRepositoryImpl;
import com.hexaware.entity.Customers;
import com.hexaware.entity.Products;
import com.hexaware.exception.CustomerNotFoundException;
import com.hexaware.exception.DbConnectionException;
import com.hexaware.exception.ProductNotFoundException;
import com.hexaware.service.ExceptionService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.fail;


public class ExceptionTest {
    OrderProcessorRepositoryImpl orderService;
    ExceptionService service;
    Customers customer;
    Products product;
    Customers customer1;
    Products product1;




    @Before
    public void before() throws DbConnectionException {
        service=new ExceptionService();
        orderService=new OrderProcessorRepositoryImpl();
        customer =new Customers();
        customer.setCustomerId(1);
        product=new Products();
        product.setProductId(1);


        Customers customer1=null;
        Products product1=null;
    }
    @After
    public void after(){
        service=null;
        orderService=null;
        customer=null;
        product=null;
        Customers customer1=null;
        Products product1=null;

    }

    @Test
    public void testAddToCartValidInputs() {
        try {
            service.addToCart1(customer, product, 1);
        } catch (Exception e) {
            fail("Exception was not expected: " + e.getMessage());
        }
    }

    @Test(expected = CustomerNotFoundException.class)
    public void testCustomerNotFoundException() throws Exception {
        service.addToCart1(customer1, product, 1);
    }

    @Test(expected = ProductNotFoundException.class)
    public void testProductNotFoundException() throws Exception {
        service.addToCart1(customer, product1, 1);
    }


    public ExceptionTest() throws DbConnectionException {
    }




}



