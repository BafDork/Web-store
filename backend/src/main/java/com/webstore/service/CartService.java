package com.webstore.service;

import com.webstore.model.Cart;
import com.webstore.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    public void addToCart(AddToCartRequest request) {
        // Логика добавления товара в корзину
    }
}