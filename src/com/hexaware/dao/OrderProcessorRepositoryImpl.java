package com.hexaware.dao;

import com.hexaware.entity.Customers;
import com.hexaware.entity.Products;
import com.hexaware.exception.CustomerNotFoundException;
import com.hexaware.exception.DbConnectionException;
import com.hexaware.exception.ProductNotFoundException;
import com.hexaware.util.DBConnection;
import com.hexaware.util.PropertyUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderProcessorRepositoryImpl implements OrderProcessorRepository{


    private Connection connection;

    public OrderProcessorRepositoryImpl() throws DbConnectionException {
        this.connection = DBConnection.getDbConnection();
    }


    @Override
    public boolean addToCart(Customers customer, Products product, int quantity) {

        String checkCustomerSql = "SELECT 1 FROM customers WHERE customer_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(checkCustomerSql)) {
            ps.setInt(1, customer.getCustomerId());
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new CustomerNotFoundException("Customer with ID " + customer.getCustomerId() + " not found.");
            }
        } catch (SQLException | CustomerNotFoundException e) {
            e.printStackTrace();
            return false;
        }


        String checkProductSql = "SELECT 1 FROM products WHERE product_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(checkProductSql)) {
            ps.setInt(1, product.getProductId());
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new ProductNotFoundException("Product with ID " + product.getProductId() + " not found.");
            }
        } catch (SQLException | ProductNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        String sql = "INSERT INTO cart (customer_id, product_id, quantity) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, customer.getCustomerId());
            ps.setInt(2, product.getProductId());
            ps.setInt(3, quantity);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean createCustomer(Customers customer) {
        String sql = "INSERT INTO customers (name, email, password) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, customer.getCustomerName());
            ps.setString(2, customer.getEmail());
            ps.setString(3, customer.getPassword());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean createProduct(Products product) {
        String sql = "INSERT INTO products (name, price, description, stockQuantity) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, product.getProductName());
            ps.setDouble(2, product.getPrice());
            ps.setString(3, product.getProductDescription());
            ps.setInt(4, product.getStockQuantity());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteCustomer(int customerId)  {
        String checkSql = "SELECT 1 FROM customers WHERE customer_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(checkSql)) {
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new CustomerNotFoundException("Customer with ID " + customerId + " not found.");
            }
        } catch (SQLException | CustomerNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        String sql = "DELETE FROM customers WHERE customer_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteProduct(int productId) {
        String checkSql = "SELECT 1 FROM products WHERE product_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(checkSql)) {
            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new ProductNotFoundException("Product with ID " + productId + " not found.");
            }
        } catch (SQLException | ProductNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        String sql = "DELETE FROM products WHERE product_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, productId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Products> getAllFromCart(Customers customer) {
        List<Products> products = new ArrayList<>();
        String sql = "SELECT p.* FROM cart c JOIN products p ON c.product_id = p.product_id WHERE c.customer_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, customer.getCustomerId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                products.add(new Products(
                        rs.getInt("product_id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getString("description"),
                        rs.getInt("stockQuantity")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public List<Map<Products, Integer>> getOrdersByCustomer(int CustomerId) {
        List<Map<Products, Integer>> orders = new ArrayList<>();
        String sql = "SELECT oi.product_id, oi.quantity, p.name, p.price, p.description, p.stockQuantity " +
                "FROM orders o " +
                "JOIN order_items oi ON o.order_id = oi.order_id " +
                "JOIN products p ON oi.product_id = p.product_id " +
                "WHERE o.customer_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, CustomerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<Products, Integer> orderItem = new HashMap<>();
                Products product = new Products(
                        rs.getInt("product_id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getString("description"),
                        rs.getInt("stockQuantity")
                );
                orderItem.put(product, rs.getInt("quantity"));
                orders.add(orderItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }


    @Override
    public boolean placeOrder(Customers customer, List<Map<Products, Integer>> productQuantityList, String shippingAddress) {
        String orderSql = "INSERT INTO orders (customer_id, order_date, total_price, shipping_address) VALUES (?, ?, ?, ?)";
        String orderItemsSql = "INSERT INTO order_items (order_id, product_id, quantity) VALUES (?, ?, ?)";
        String clearCartSql = "DELETE FROM cart WHERE customer_id = ?";

        try {
            connection.setAutoCommit(false);
            double totalPrice = 0.0;

            for (Map<Products, Integer> map : productQuantityList) {
                for (Map.Entry<Products, Integer> entry : map.entrySet()) {
                    totalPrice += entry.getKey().getPrice() * entry.getValue();
                }
            }

            PreparedStatement orderPs = connection.prepareStatement(orderSql, Statement.RETURN_GENERATED_KEYS);
            orderPs.setInt(1, customer.getCustomerId());
            orderPs.setDate(2, new java.sql.Date(System.currentTimeMillis()));
            orderPs.setDouble(3, totalPrice);
            orderPs.setString(4, shippingAddress);
            orderPs.executeUpdate();

            ResultSet rs = orderPs.getGeneratedKeys();
            int orderId = 0;
            if (rs.next()) {
                orderId = rs.getInt(1);
            }

            PreparedStatement itemPs = connection.prepareStatement(orderItemsSql);
            for (Map<Products, Integer> map : productQuantityList) {
                for (Map.Entry<Products, Integer> entry : map.entrySet()) {
                    itemPs.setInt(1, orderId);
                    itemPs.setInt(2, entry.getKey().getProductId());
                    itemPs.setInt(3, entry.getValue());
                    itemPs.addBatch();
                }
            }
            itemPs.executeBatch();

            PreparedStatement clearCart = connection.prepareStatement(clearCartSql);
            clearCart.setInt(1, customer.getCustomerId());
            clearCart.executeUpdate();

            connection.commit();
            return true;

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException autoCommitEx) {
                autoCommitEx.printStackTrace();
            }
        }
    }

    @Override
    public boolean removeFromCart(Customers customer, Products product) {
        String sql = "DELETE FROM cart WHERE customer_id = ? AND product_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, customer.getCustomerId());
            ps.setInt(2, product.getProductId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
