package com.webstore.service;

import com.webstore.model.Cart;
import com.webstore.model.Product;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CartService {

    private Map<Long, Cart> userCarts = new HashMap<>();

    public Cart getCartByUserId(Long userId) {
        return userCarts.getOrDefault(userId, new Cart(userId));
    }

    public void addProductToCart(Long userId, Product product, int quantity) {
        Cart cart = userCarts.getOrDefault(userId, new Cart(userId));
        cart.addProduct(product, quantity);
        userCarts.put(userId, cart);
    }

    public void removeProductFromCart(Long userId, Product product) {
        Cart cart = userCarts.get(userId);
        if (cart != null) {
            cart.removeProduct(product);
        }
    }

    public void updateProductQuantity(Long userId, Product product, int quantity) {
        Cart cart = userCarts.get(userId);
        if (cart != null) {
            cart.updateProductQuantity(product, quantity);
        }
    }
}