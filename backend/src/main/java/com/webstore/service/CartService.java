package com.webstore.service;

import com.webstore.model.Cart;
import com.webstore.model.Product;
import com.webstore.model.User;
import com.webstore.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final UserService userService;
    private final ProductService productService;

    public Cart getCart() {
        User user = userService.getCurrentUser();
        return cartRepository.findByUserId(user.getId());
    }

    public Cart save(Cart cart) {
        return cartRepository.save(cart);
    }

    public void addProductToCart(Long productId, int quantity) {
        User user = userService.getCurrentUser();
        Cart cart = cartRepository.findByUserId(user.getId());

        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
        }

        Product product = productService.getProductById(productId);
        cart.addProduct(product, quantity);
        save(cart);
    }

    public void removeProductFromCart(Long productId) {
        User user = userService.getCurrentUser();
        Cart cart = cartRepository.findByUserId(user.getId());
        if (cart != null) {
            Product product = productService.getProductById(productId);
            cart.removeProduct(product);
            save(cart);
        }
    }

    public void updateProductQuantity(Long productId, int quantity) {
        User user = userService.getCurrentUser();
        Cart cart = cartRepository.findByUserId(user.getId());
        if (cart != null) {
            Product product = productService.getProductById(productId);
            cart.updateProduct(product, quantity);
            save(cart);
        }
    }
}
