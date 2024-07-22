package com.dheeraj.beauty.service;

import com.dheeraj.beauty.model.Cart;
import com.dheeraj.beauty.model.Order;
import com.dheeraj.beauty.model.Product;
import com.dheeraj.beauty.model.User;
import com.dheeraj.beauty.repository.CartRepository;
import com.dheeraj.beauty.repository.ProductRepository;
import com.dheeraj.beauty.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    public Cart getCart() {
        User user = getLoggedInUser();
        return cartRepository.findByUser(user).orElse(new Cart());
    }

    public void addProductToCart(Long productId) {
        User user = getLoggedInUser();
        Cart cart = cartRepository.findByUser(user).orElse(new Cart());
        Optional<Product> productOpt = productRepository.findById(productId);

        if (!productOpt.isPresent()) {
            throw new RuntimeException("Product not found");
        }

        cart.getProducts().add(productOpt.get());
        cart.setUser(user);
        cartRepository.save(cart);
    }

    public void checkout(String address, String paymentMethod) {
        User user = getLoggedInUser();
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new RuntimeException("Cart not found"));

        if (cart.getProducts().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Order order = new Order();
        order.setUser(user);
        order.setProducts(cart.getProducts());
        order.setAddress(address);
        order.setPaymentMethod(paymentMethod);

        cart.getProducts().clear();
        cartRepository.save(cart);
    }

    private User getLoggedInUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
    }
}
