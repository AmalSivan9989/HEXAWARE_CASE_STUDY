package com.hexaware.test;

import com.hexaware.exception.DbConnectionException;
import com.hexaware.service.ProductAddedService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ProductAddedTest {
    ProductAddedService service;


    @Before
    public void before() throws DbConnectionException {
        service= new ProductAddedService();
    }

    @After
    public void after(){
        service=null;
    }

    @Test
    public void productAdded1(){
        boolean actual=service.addToCart1();
        assertTrue("Product added due to valid id",actual);
    }


    @Test
    public void productAdded2(){
        boolean actual=service.addToCart2();
        assertFalse("Product not added due to invalid id",actual);
    }
}
