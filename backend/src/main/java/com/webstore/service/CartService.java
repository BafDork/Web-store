package com.webstore.service;

import com.webstore.dto.response.CartProductResponseDTO;
import com.webstore.dto.response.ProductResponseDTO;
import com.webstore.model.Cart;
import com.webstore.model.Product;
import com.webstore.model.User;
import com.webstore.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final UserService userService;
    private final ProductService productService;

    public Cart getCurrentUserCart() {
        User user = userService.getCurrentUser();
        Cart cart = cartRepository.findByUserId(user.getId());
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            saveCart(cart);
        }
        return cart;
    }

    public List<CartProductResponseDTO> getCartProducts() {
        Cart cart = getCurrentUserCart();

        List<CartProductResponseDTO> cartProducts = new ArrayList<>();
        List<Product> products = cart.getProducts();
        List<Integer> quantities = cart.getQuantities();

        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            int quantity = quantities.get(i);
            cartProducts.add(new CartProductResponseDTO(
                    new ProductResponseDTO(product),
                    quantity
            ));
        }

        return cartProducts;
    }

    public void addProductToCart(Long productId, int quantity) {
        Cart cart = getCurrentUserCart();

        Product product = productService.getProductById(productId);
        cart.addProduct(product, quantity);
        saveCart(cart);
    }

    public void removeProductFromCart(Long productId) {
        Cart cart = getCurrentUserCart();

        Product product = productService.getProductById(productId);
        cart.removeProduct(product);
        saveCart(cart);
    }

    public void updateProductQuantity(Long productId, int quantity) {
        Cart cart = getCurrentUserCart();

        Product product = productService.getProductById(productId);
        cart.updateProduct(product, quantity);
        saveCart(cart);
    }

    public void saveCart(Cart cart) {
        cartRepository.save(cart);
    }
}
