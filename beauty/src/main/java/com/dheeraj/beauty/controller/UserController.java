package com.dheeraj.beauty.controller;

import com.dheeraj.beauty.service.CartService;
import com.dheeraj.beauty.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CartService cartService;

    @GetMapping("/products")
    public String viewProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "user/product_list";
    }

    @PostMapping("/cart")
    public String addToCart(@RequestParam("productId") Long productId) {
        cartService.addProductToCart(productId);
        return "redirect:/user/cart";
    }

    @GetMapping("/cart")
    public String viewCart(Model model) {
        model.addAttribute("cart", cartService.getCart());
        return "user/cart";
    }

    @GetMapping("/checkout")
    public String checkout(Model model) {
        return "user/checkout";
    }

    @PostMapping("/checkout")
    public String processCheckout(@RequestParam("address") String address, @RequestParam("paymentMethod") String paymentMethod) {
        cartService.checkout(address, paymentMethod);
        return "redirect:/user/products";
    }
}