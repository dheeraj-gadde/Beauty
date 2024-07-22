package com.dheeraj.beauty.service;

import com.dheeraj.beauty.model.Order;
import com.dheeraj.beauty.model.Product;
import com.dheeraj.beauty.model.User;
import com.dheeraj.beauty.repository.OrderRepository;
import com.dheeraj.beauty.repository.ProductRepository;
import com.dheeraj.beauty.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    public void placeOrder(Long userId, Set<Long> productIds, String address, String paymentMethod) {
        // Fetch the user
        Optional<User> userOpt = userRepository.findById(userId);
        if (!userOpt.isPresent()) {
            throw new RuntimeException("User not found");
        }
        User user = userOpt.get();

        // Fetch the products
        Set<Product> products = productRepository.findAllByIdIn(productIds);
        if (products.isEmpty()) {
            throw new RuntimeException("No products found");
        }

        // Create a new order
        Order order = new Order();
        order.setUser(user);
        order.setProducts(products);
        order.setAddress(address);
        order.setPaymentMethod(paymentMethod);

        // Save the order
        orderRepository.save(order);
    }
}
