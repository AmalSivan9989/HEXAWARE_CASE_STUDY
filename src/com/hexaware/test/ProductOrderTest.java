package com.hexaware.test;

import com.hexaware.exception.DbConnectionException;
import com.hexaware.service.ProductOrderService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ProductOrderTest {

    ProductOrderService service;

    @Before
    public void before() throws DbConnectionException {
        service=new ProductOrderService();
    }
    @After
    public void after(){
        service=null;
    }
    @Test
    public void testPlaceOrder1(){
        boolean actual=service.testPlaceOrder1();
        assertTrue("Order placed successfully!",actual);
    }
    @Test
    public void testPlaceOrder2(){
        boolean actual=service.testPlaceOrder2();
        assertFalse("Test case failed due to invalid customer and product id",actual);
    }
}
