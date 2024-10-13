//package com.webstore.controller;
//
//import com.webstore.model.Cart;
//import com.webstore.model.Product;
//import com.webstore.service.CartService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/cart")
//public class CartController {
//
//    @Autowired
//    private CartService cartService;
//
//    @GetMapping("/{userId}")
//    public Cart getCart(@PathVariable Long userId) {
//        return cartService.getCartByUserId(userId);
//    }
//
//    @PostMapping("/{userId}/add")
//    public void addToCart(@PathVariable Long userId, @RequestBody Product product, @RequestParam int quantity) {
//        cartService.addProductToCart(userId, product, quantity);
//    }
//
//    @PostMapping("/{userId}/update")
//    public void updateCart(@PathVariable Long userId, @RequestBody Product product, @RequestParam int quantity) {
//        cartService.updateProductQuantity(userId, product, quantity);
//    }
//
//    @DeleteMapping("/{userId}/remove")
//    public void removeFromCart(@PathVariable Long userId, @RequestBody Product product) {
//        cartService.removeProductFromCart(userId, product);
//    }
//}
//
