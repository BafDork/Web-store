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

    /**
     * Получает корзину текущего пользователя. Если корзины нет, она создается и сохраняется.
     *
     * @return корзина текущего пользователя
     */
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

    /**
     * Получает список продуктов в корзине текущего пользователя.
     *
     * @return список DTO продуктов в корзине
     */
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

    /**
     * Добавляет продукт в корзину пользователя с указанным количеством.
     *
     * @param productId ID продукта
     * @param quantity количество продукта
     */
    public void addProductToCart(Long productId, int quantity) {
        Cart cart = getCurrentUserCart();
        Product product = productService.getProductById(productId);
        cart.addProduct(product, quantity);
        saveCart(cart);
    }

    /**
     * Удаляет продукт из корзины пользователя.
     *
     * @param productId ID продукта
     */
    public void removeProductFromCart(Long productId) {
        Cart cart = getCurrentUserCart();
        Product product = productService.getProductById(productId);
        cart.removeProduct(product);
        saveCart(cart);
    }

    /**
     * Обновляет количество продукта в корзине пользователя.
     *
     * @param productId ID продукта
     * @param quantity новое количество
     */
    public void updateProductQuantity(Long productId, int quantity) {
        Cart cart = getCurrentUserCart();
        Product product = productService.getProductById(productId);
        cart.updateProduct(product, quantity);
        saveCart(cart);
    }

    /**
     * Очищает корзину пользователя.
     *
     * @param cart корзина пользователя
     */
    public void clearCart(Cart cart) {
        cart.getProducts().clear();
        cart.getQuantities().clear();
        saveCart(cart);
    }

    /**
     * Сохраняет корзину в базе данных.
     *
     * @param cart корзина для сохранения
     */
    public void saveCart(Cart cart) {
        cartRepository.save(cart);
    }
}

