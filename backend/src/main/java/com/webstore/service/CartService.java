package com.webstore.service;

import com.webstore.model.Cart;
import com.webstore.model.Product;
import com.webstore.model.User;
import com.webstore.repository.CartRepository;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    public void addProductToCart(Long userId, Long productId, int quantity) {
        User user = userService.getUserById(userId);
        Cart cart = cartRepository.findByUserId(userId);

        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
        }

        Product product = productService.getProductById(productId);
        cart.addProduct(product, quantity);
        cartRepository.save(cart);
    }

    public void removeProductFromCart(Long userId, Long productId) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart != null) {
            Product product = productService.getProductById(productId);
            cart.removeProduct(product);
            cartRepository.save(cart);
        }
    }
}
