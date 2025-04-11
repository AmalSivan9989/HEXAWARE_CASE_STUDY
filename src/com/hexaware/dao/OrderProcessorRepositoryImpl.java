package com.hexaware.dao;

import com.hexaware.entity.Customers;
import com.hexaware.entity.Products;
import com.hexaware.exception.CustomerNotFoundException;
import com.hexaware.exception.DbConnectionException;
import com.hexaware.exception.ProductNotFoundException;
import com.hexaware.util.DBConnection;
import com.hexaware.util.HexaConstants;


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
        String checkCustomerSql = HexaConstants.GET_CUSTOMER_BY_ID;
        try (PreparedStatement ps = connection.prepareStatement(checkCustomerSql)) {
            ps.setInt(1, customer.getCustomerId());
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new CustomerNotFoundException(HexaConstants.CUSTOMER_WITH_ID+ customer.getCustomerId() + HexaConstants.NOT_FOUND);
            }
        } catch (SQLException | CustomerNotFoundException e) {
           // e.printStackTrace();
            return false;
        }


        String checkProductSql = HexaConstants.GET_PRODUCT_BY_ID;
        try (PreparedStatement ps = connection.prepareStatement(checkProductSql)) {
            ps.setInt(1, product.getProductId());
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new ProductNotFoundException(HexaConstants.PRODUCT_WITH_ID+ product.getProductId() + HexaConstants.NOT_FOUND);
            }
        } catch (SQLException | ProductNotFoundException e) {
          //  e.printStackTrace();
            return false;
        }
        String sql = HexaConstants.INSERT_VALUES_INTO_CART;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, customer.getCustomerId());
            ps.setInt(2, product.getProductId());
            ps.setInt(3, quantity);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
          //  e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean createCustomer(Customers customer) {
        String sql = HexaConstants.INSERT_VALUES_INTO_CUSTOMER;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, customer.getCustomerName());
            ps.setString(2, customer.getEmail());
            ps.setString(3, customer.getPassword());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
        //    e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean createProduct(Products product){
        String sql = HexaConstants.INSERT_VALUES_INTO_PRODUCT;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, product.getProductName());
            ps.setDouble(2, product.getPrice());
            ps.setString(3, product.getProductDescription());
            ps.setInt(4, product.getStockQuantity());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
           // e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteCustomer(int customerId)  {
        String checkSql = HexaConstants.CHECK_FOR_CUSTOMER_AVAILABILITY;
        try (PreparedStatement ps = connection.prepareStatement(checkSql)) {
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new CustomerNotFoundException(HexaConstants.CUSTOMER_WITH_ID+ customerId +HexaConstants.NOT_FOUND);
            }
        } catch (SQLException | CustomerNotFoundException e) {
         //   e.printStackTrace();
            return false;
        }
        String sql = HexaConstants.DELETE_CUSTOMER_BY_ID;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
       //     e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteProduct(int productId) {
        String checkSql = HexaConstants.CHECK_FOR_PRODUCT_AVAILABILITY;
        try (PreparedStatement ps = connection.prepareStatement(checkSql)) {
            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new ProductNotFoundException(HexaConstants.PRODUCT_WITH_ID+ productId +HexaConstants.NOT_FOUND);
            }
        } catch (SQLException | ProductNotFoundException e) {
          //  e.printStackTrace();
            return false;
        }
        String sql = HexaConstants.DELETE_PRODUCT_BY_ID;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, productId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
           // e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Products> getAllFromCart(Customers customer) {
        List<Products> products = new ArrayList<>();
        String sql = HexaConstants.GET_PRODUCT_BY_CUSTOMER_ID;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, customer.getCustomerId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                products.add(new Products(
                        rs.getInt(HexaConstants.PRODUCT_ID),
                        rs.getString(HexaConstants.PRODUCT_NAME),
                        rs.getDouble(HexaConstants.PRODUCT_PRICE),
                        rs.getString(HexaConstants.PRODUCT_DESCRIPTION),
                        rs.getInt(HexaConstants.PRODUCT_STOCK_QUANTITY)
                ));
            }
        } catch (SQLException e) {
         //   e.printStackTrace();
        }
        return products;
    }

    @Override
    public List<Map<Products, Integer>> getOrdersByCustomer(int CustomerId) {
        List<Map<Products, Integer>> orders = new ArrayList<>();
        String sql = HexaConstants.GET_ORDERS_BY_CUSTOMER;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, CustomerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<Products, Integer> orderItem = new HashMap<>();
                Products product = new Products(
                        rs.getInt(HexaConstants.PRODUCT_ID),
                        rs.getString(HexaConstants.PRODUCT_NAME),
                        rs.getDouble(HexaConstants.PRODUCT_PRICE),
                        rs.getString(HexaConstants.PRODUCT_DESCRIPTION),
                        rs.getInt(HexaConstants.PRODUCT_STOCK_QUANTITY)
                );
                orderItem.put(product, rs.getInt(HexaConstants.QUANTITY));
                orders.add(orderItem);
            }
        } catch (SQLException e) {
          //  e.printStackTrace();
        }
        return orders;
    }


    @Override
    public boolean placeOrder(Customers customer, List<Map<Products, Integer>> productQuantityList, String shippingAddress) {
        String orderSql = HexaConstants.INSERT_VALUES_INTO_ORDER;
        String orderItemsSql = HexaConstants.INSERT_VALUES_INTO_ORDER_DETAILS;
        String clearCartSql = HexaConstants.DELETE_CART_BY_CUSTOMER_ID;

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
               // rollbackEx.printStackTrace();
            }
          //  e.printStackTrace();
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException autoCommitEx) {
              //  autoCommitEx.printStackTrace();
            }
        }
    }

    @Override
    public boolean removeFromCart(Customers customer, Products product) {
        String sql = HexaConstants.DELETE_CUSTOMER_BY_CUSTOMER_ID_AND_PRODUCT_ID;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, customer.getCustomerId());
            ps.setInt(2, product.getProductId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
           // e.printStackTrace();
            return false;
        }
    }
}
