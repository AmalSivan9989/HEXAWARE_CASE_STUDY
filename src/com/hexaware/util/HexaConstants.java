package com.hexaware.util;

public class HexaConstants {


    //DB CONNECTIVITY
    public static final String DB_DRIVER = "db.driver";
    public static final String DB_URL = "db.url";
    public static final String DB_USER = "db.user";
    public static final String DB_PASSWORD = "db.password";
    public static final String CANNOT_OPEN_CONNECTION = "Cannot Open Connection";
    public static final String DB_FILE_NAME = "src/hexa.properties";

    //QUERIES

    //SELECT
    public static final String GET_CUSTOMER_BY_ID = "SELECT 1 FROM customers WHERE customer_id = ?";
    public static final String GET_PRODUCT_BY_ID = "SELECT 1 FROM products WHERE product_id = ?";
    public static final String GET_PRODUCT_BY_CUSTOMER_ID = "SELECT p.* FROM cart c JOIN products p ON c.product_id = p.product_id WHERE c.customer_id = ?";
    public static final String GET_ORDERS_BY_CUSTOMER = "SELECT oi.product_id, oi.quantity, p.name, p.price, p.description, p.stockQuantity " +
            "FROM orders o " +
            "JOIN order_items oi ON o.order_id = oi.order_id " +
            "JOIN products p ON oi.product_id = p.product_id " +
            "WHERE o.customer_id = ?";

    //INSERT QUERIES
    public static final String INSERT_VALUES_INTO_CART = "INSERT INTO cart (customer_id, product_id, quantity) VALUES (?, ?, ?)";
    public static final String INSERT_VALUES_INTO_CUSTOMER = "INSERT INTO customers (name, email, password) VALUES (?, ?, ?)";
    public static final String INSERT_VALUES_INTO_PRODUCT = "INSERT INTO products (name, price, description, stockQuantity) VALUES (?, ?, ?, ?)";
    public static final String INSERT_VALUES_INTO_ORDER = "INSERT INTO orders (customer_id, order_date, total_price, shipping_address) VALUES (?, ?, ?, ?)";
    public static final String INSERT_VALUES_INTO_ORDER_DETAILS = "INSERT INTO order_items (order_id, product_id, quantity) VALUES (?, ?, ?)";

    //CHECK QUERIES
    public static final String CHECK_FOR_CUSTOMER_AVAILABILITY = "SELECT 1 FROM customers WHERE customer_id = ?";
    public static final String CHECK_FOR_PRODUCT_AVAILABILITY = "SELECT 1 FROM products WHERE product_id = ?";


    //DELETE QUERIES
    public static final String DELETE_CUSTOMER_BY_ID = "DELETE FROM customers WHERE customer_id = ?";
    public static final String DELETE_PRODUCT_BY_ID = "DELETE FROM products WHERE product_id = ?";
    public static final String DELETE_CART_BY_CUSTOMER_ID="DELETE FROM cart WHERE customer_id = ?";
    public static final String DELETE_CUSTOMER_BY_CUSTOMER_ID_AND_PRODUCT_ID="DELETE FROM cart WHERE customer_id = ? AND product_id = ?";

    //VARIABLE NAMES
    public static final String DELIMITER=",";
    public static final String ORDER_ID="";
    public static final String CUSTOMER_WITH_ID="Customer with ID ";
    public static final String PRODUCT_WITH_ID="Product with ID ";
    public static final String PRODUCT_ID="product_id";
    public static final String PRODUCT_NAME="name";
    public static final String PRODUCT_PRICE="price";
    public static final String PRODUCT_DESCRIPTION="description";
    public static final String PRODUCT_STOCK_QUANTITY="stockQuantity";
    public static final String QUANTITY="quantity";
    public static final String NOT_FOUND=" not found.";
}


