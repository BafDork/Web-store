package com.webstore.service.unit;

import com.webstore.dto.response.CartProductResponseDTO;
import com.webstore.model.Cart;
import com.webstore.model.Product;
import com.webstore.model.Role;
import com.webstore.model.User;
import com.webstore.repository.CartRepository;
import com.webstore.service.CartService;
import com.webstore.service.ProductService;
import com.webstore.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private UserService userService;

    @Mock
    private ProductService productService;

    @InjectMocks
    private CartService cartService;

    private User user;
    private Product product;
    private Cart cart;
    private AutoCloseable mocks;

    @BeforeEach
    void setUp() {
        mocks = MockitoAnnotations.openMocks(this);

        user = new User(1L, "Test", "User", "test@example.com",
                "password", Role.ROLE_USER);
        product = new Product(1L, "Product1", "Description", 100.0,
                null, 1, "Image", "", Collections.emptySet());
        cart = new Cart();
        cart.setUser(user);
    }

    @AfterEach
    void tearDown() throws Exception {
        if (mocks != null) {
            mocks.close();
        }
    }

    /**
     * Тестирует метод getCurrentUserCart, который должен вернуть корзину текущего пользователя.
     * Если корзины нет, она должна быть создана и сохранена.
     */
    @Test
    void getCurrentUserCart_ShouldReturnCart_WhenCartExists() {
        when(userService.getCurrentUser()).thenReturn(user);
        when(cartRepository.findByUserId(user.getId())).thenReturn(cart);

        Cart result = cartService.getCurrentUserCart();

        assertNotNull(result);
        assertEquals(user.getId(), result.getUser().getId());
        verify(cartRepository, never()).save(any(Cart.class));
    }

    @Test
    void getCurrentUserCart_ShouldCreateNewCart_WhenCartDoesNotExist() {
        when(userService.getCurrentUser()).thenReturn(user);
        when(cartRepository.findByUserId(user.getId())).thenReturn(null);

        Cart result = cartService.getCurrentUserCart();

        assertNotNull(result);
        assertEquals(user.getId(), result.getUser().getId());
        verify(cartRepository, times(1)).save(any(Cart.class));
    }

    /**
     * Тестирует метод getCartProducts, который должен вернуть список продуктов в корзине.
     */
    @Test
    void getCartProducts_ShouldReturnProductList() {
        cart.addProduct(product, 2);
        when(userService.getCurrentUser()).thenReturn(user);
        when(cartRepository.findByUserId(user.getId())).thenReturn(cart);

        List<CartProductResponseDTO> result = cartService.getCartProducts();

        assertEquals(1, result.size());
        assertEquals("Product1", result.get(0).getProduct().getName());
        assertEquals(2, result.get(0).getQuantity());
    }

    /**
     * Тестирует метод addProductToCart, который должен добавлять продукт в корзину.
     */
    @Test
    void addProductToCart_ShouldAddProduct() {
        when(userService.getCurrentUser()).thenReturn(user);
        when(cartRepository.findByUserId(user.getId())).thenReturn(cart);
        when(productService.getProductById(1L)).thenReturn(product);

        cartService.addProductToCart(1L, 3);

        assertEquals(1, cart.getItems().size());
        assertEquals(3, cart.getItems().get(0).getQuantity());
    }

    /**
     * Тестирует метод removeProductFromCart, который должен удалять продукт из корзины.
     */
    @Test
    void removeProductFromCart_ShouldRemoveProduct() {
        cart.addProduct(product, 2);
        when(userService.getCurrentUser()).thenReturn(user);
        when(cartRepository.findByUserId(user.getId())).thenReturn(cart);
        when(productService.getProductById(1L)).thenReturn(product);

        cartService.removeProductFromCart(1L);

        assertTrue(cart.getItems().isEmpty());
    }

    /**
     * Тестирует метод updateProductQuantity, который должен обновлять количество продукта в корзине.
     */
    @Test
    void updateProductQuantity_ShouldUpdateQuantity() {
        cart.addProduct(product, 2);
        when(userService.getCurrentUser()).thenReturn(user);
        when(cartRepository.findByUserId(user.getId())).thenReturn(cart);
        when(productService.getProductById(1L)).thenReturn(product);

        cartService.updateProductQuantity(1L, 5);

        assertEquals(5, cart.getItems().get(0).getQuantity());
    }

    /**
     * Тестирует метод clearCart, который должен очищать корзину.
     */
    @Test
    void clearCart_ShouldClearCart() {
        cart.addProduct(product, 2);
        when(userService.getCurrentUser()).thenReturn(user);
        when(cartRepository.findByUserId(user.getId())).thenReturn(cart);

        cartService.clearCart(cart);

        assertTrue(cart.getItems().isEmpty());
    }

    /**
     * Тестирует метод saveCart, который должен сохранять корзину.
     */
    @Test
    void saveCart_ShouldSaveCart() {
        when(cartRepository.save(cart)).thenReturn(cart);

        cartService.saveCart(cart);

        verify(cartRepository, times(1)).save(cart);
    }
}