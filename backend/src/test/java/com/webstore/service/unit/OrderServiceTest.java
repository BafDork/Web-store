package com.webstore.service.unit;

import com.webstore.dto.response.OrderResponseDTO;
import com.webstore.exceptions.OutOfStockException;
import com.webstore.model.Cart;
import com.webstore.model.Order;
import com.webstore.model.Product;
import com.webstore.model.User;
import com.webstore.repository.OrderRepository;
import com.webstore.service.CartService;
import com.webstore.service.EmailService;
import com.webstore.service.OrderService;
import com.webstore.service.ProductService;
import com.webstore.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock private UserService userService;
    @Mock private ProductService productService;
    @Mock private CartService cartService;
    @Mock private OrderRepository orderRepository;
    @Mock private EmailService emailService;

    @InjectMocks private OrderService orderService;

    /**
     * Тестирует проверку наличия товаров на складе.
     * Ожидается, что будет выброшено исключение, если запрашиваемое количество товара превышает его доступный запас.
     */
    @Test
    void validateStockForCart_ShouldThrowException_WhenStockIsInsufficient() {
        Product product = new Product();
        product.setName("Test Product");
        product.setStock(1);

        Cart cart = new Cart();
        cart.setProducts(Collections.singletonList(product));
        cart.setQuantities(Collections.singletonList(2));

        OutOfStockException exception = assertThrows(OutOfStockException.class, () -> orderService.validateStockForCart(cart));

        assertEquals("Товара Test Product недостаточно на складе.", exception.getMessage());
    }

    /**
     * Тестирует создание заказа из корзины.
     * Ожидается, что заказ будет успешно создан, товары на складе будут обновлены, а заказ сохранен в репозитории.
     */
    @Test
    void createOrderFromCart_ShouldCreateOrder_WhenStockIsSufficient() {
        Product product = new Product();
        product.setName("Test Product");
        product.setPrice(100.0);
        product.setStock(5);

        Cart cart = new Cart();
        cart.setProducts(Collections.singletonList(product));
        cart.setQuantities(Collections.singletonList(2));

        User user = new User();
        user.setId(1L);

        when(userService.getCurrentUser()).thenReturn(user);

        Order order = orderService.createOrderFromCart(cart);

        assertNotNull(order);
        assertEquals(1, order.getItems().size());
        assertEquals(200.0, order.getTotalAmount(), 0.01);
        verify(productService).saveProduct(product);
        verify(orderRepository).save(order);
    }

    /**
     * Тестирует отправку подтверждения заказа.
     * Ожидается, что будет отправлено письмо на email текущего пользователя.
     */
    @Test
    void sendOrderConfirmation_ShouldSendEmail() {
        Order order = new Order();
        User user = new User();
        user.setEmail("test@example.com");

        when(userService.getCurrentUser()).thenReturn(user);

        orderService.sendOrderConfirmation(order);

        verify(emailService).sendOrderConfirmation("test@example.com", order);
    }

    /**
     * Тестирует метод оформления заказа.
     * Ожидается, что заказ будет создан, подтверждение отправлено, а корзина очищена.
     */
    @Test
    void checkout_ShouldCreateOrderAndSendConfirmation() {
        Product product = new Product();
        product.setName("Test Product");
        product.setPrice(100.0);
        product.setStock(5);

        Cart cart = new Cart();
        cart.setProducts(Collections.singletonList(product));
        cart.setQuantities(Collections.singletonList(2));

        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");

        when(cartService.getCurrentUserCart()).thenReturn(cart);
        when(userService.getCurrentUser()).thenReturn(user);

        OrderResponseDTO response = orderService.checkout();

        assertNotNull(response);
        verify(cartService).getCurrentUserCart();
        verify(orderRepository).save(any(Order.class));
        verify(emailService).sendOrderConfirmation(eq("test@example.com"), any(Order.class));
        verify(cartService).clearCart(cart);
    }
}
