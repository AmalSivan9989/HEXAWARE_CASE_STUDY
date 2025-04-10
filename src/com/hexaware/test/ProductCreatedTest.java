package com.hexaware.test;
import com.hexaware.exception.DbConnectionException;
import com.hexaware.service.ProductCreatedService;
import org.junit.*;

import static org.junit.Assert.*;

public class ProductCreatedTest {

    ProductCreatedService service;
    static boolean expected;

    @Before
    public void before() throws DbConnectionException {
        service =new ProductCreatedService();
    }
    @BeforeClass
    public static void beforeClass(){
        expected=true;
    }

    @After
    public void after(){
        service=null;
    }

    @AfterClass
    public static void afterClass(){
        expected=false;
    }


    @Test
    public void testProductCreation(){
        boolean actual=service.testProductCreation1();
        assertEquals("Product created and test case passed!",expected,actual);
    }

    @Test
    public void testProductFailure(){
        boolean actual=service.testProductCreation2();
        assertEquals("product created but test case failed",expected,actual);

    }

}
