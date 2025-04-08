package com.hexaware.main;

import com.hexaware.dao.OrderProcessorRepository;
import com.hexaware.dao.OrderProcessorRepositoryImpl;
import com.hexaware.entity.Customers;
import com.hexaware.entity.Products;
import com.hexaware.exception.CustomerNotFoundException;
import com.hexaware.exception.DbConnectionException;
import com.hexaware.exception.ProductNotFoundException;

import java.util.*;

public class EcomApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            OrderProcessorRepository service = new OrderProcessorRepositoryImpl();

            while (true) {
                System.out.println("\n========= E-Commerce Application =========");
                System.out.println("1. Register Customer");
                System.out.println("2. Create Product");
                System.out.println("3. Delete Product");
                System.out.println("4. Add to Cart");
                System.out.println("5. View Cart");
                System.out.println("6. Place Order");
                System.out.println("7. View Customer Order");
                System.out.println("8. Exit");
                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        sc.nextLine();
                        System.out.print("Enter name: ");
                        String name = sc.nextLine();
                        System.out.print("Enter email: ");
                        String email = sc.nextLine();
                        System.out.print("Enter password: ");
                        String password = sc.nextLine();
                        Customers customer = new Customers(name, email, password);
                        boolean custCreated = service.createCustomer(customer);
                        System.out.println(custCreated ? "Customer registered." : "Failed to register customer.");
                        break;

                    case 2:
                        sc.nextLine();
                        System.out.print("Enter product name: ");
                        String productName = sc.nextLine();
                        System.out.print("Enter price: ");
                        double price = sc.nextDouble();
                        sc.nextLine();
                        System.out.print("Enter description: ");
                        String desc = sc.nextLine();
                        System.out.print("Enter stock quantity: ");
                        int stock = sc.nextInt();
                        Products product = new Products(productName, price, desc, stock);
                        boolean prodCreated = service.createProduct(product);
                        System.out.println(prodCreated ? "Product created." : "Failed to create product.");
                        break;

                    case 3:
                        System.out.print("Enter product ID to delete: ");
                        int prodId = sc.nextInt();
                        boolean deleted = service.deleteProduct(prodId);
                        System.out.println(deleted ? "Product deleted." : "Product not found.");
                        break;

                    case 4:
                        System.out.print("Enter customer ID: ");
                        int custId = sc.nextInt();
                        System.out.print("Enter product ID: ");
                        int pId = sc.nextInt();
                        System.out.print("Enter quantity: ");
                        int qty = sc.nextInt();
                        customer = new Customers();
                        customer.setCustomerId(custId);
                        product = new Products();
                        product.setProductId(pId);
                        boolean added = service.addToCart(customer, product, qty);
                        System.out.println(added ? "Product added to cart." : "Failed to add to cart.");
                        break;

                    case 5:
                        System.out.print("Enter customer ID to view cart: ");
                        int cId = sc.nextInt();
                        customer = new Customers();
                        customer.setCustomerId(cId);
                        List<Products> cart = service.getAllFromCart(customer);
                        if (cart.isEmpty()) {
                            System.out.println("Cart is empty.");
                        } else {
                            cart.forEach(System.out::println);
                        }
                        break;

                    case 6:
                        System.out.print("Enter customer ID to place order: ");
                        int custIdForOrder = sc.nextInt();
                        System.out.print("Enter Product ID: ");
                        int productId = sc.nextInt();
                        System.out.print("Enter Quantity: ");
                        int quantity = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter Payment Mode (e.g., 'Cash', 'Credit Card'): ");
                        String paymentMode = sc.nextLine();

                        customer = new Customers();
                        customer.setCustomerId(custIdForOrder);

                        product = new Products();
                        product.setProductId(productId);

                        Map<Products, Integer> item = new HashMap<>();
                        item.put(product, quantity);
                        List<Map<Products, Integer>> cartItems = new ArrayList<>();
                        cartItems.add(item);

                        boolean placed = service.placeOrder(customer, cartItems, paymentMode);
                        System.out.println(placed ? "Order placed successfully." : "Order placement failed.");
                        break;

                    case 7:
                        System.out.print("Enter customer ID to view orders: ");
                        int custOrderId = sc.nextInt();
                        List<Map<Products, Integer>> orders = service.getOrdersByCustomer(custOrderId);
                        if (orders.isEmpty()) {
                            System.out.println("No orders found.");
                        } else {
                            for (Map<Products, Integer> order : orders) {
                                for (Map.Entry<Products, Integer> entry : order.entrySet()) {
                                    System.out.println("Product: " + entry.getKey() + ", Quantity: " + entry.getValue());
                                }
                            }
                        }
                        break;

                    case 8:
                        System.out.println("Exiting...");
                        sc.close();
                        System.exit(0);

                    default:
                        System.out.println("Invalid choice.");
                }
            }

        } catch (DbConnectionException e) {
            System.out.println("Database connection failed: " + e.getMessage());
        }
    }
}
