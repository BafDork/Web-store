package com.webstore.service;

import com.webstore.dto.response.OrderResponseDTO;
import com.webstore.exceptions.OutOfStockException;
import com.webstore.model.Cart;
import com.webstore.model.Order;
import com.webstore.model.OrderItem;
import com.webstore.model.Product;
import com.webstore.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final UserService userService;
    private final ProductService productService;
    private final CartService cartService;
    private final OrderRepository orderRepository;
    private final EmailService emailService;

    public OrderResponseDTO checkout() {
        Cart cart = cartService.getCurrentUserCart();

        validateStockForCart(cart);
        Order order = createOrderFromCart(cart);
        sendOrderConfirmation(order);

        cartService.clearCart(cart);
        return new OrderResponseDTO(order.getId());
    }

    /**
     * Проверяет наличие товаров из корзины на складе.
     *
     * @param cart текущая корзина пользователя
     */
    public void validateStockForCart(Cart cart) {
        List<Product> products = cart.getProducts();
        List<Integer> quantities = cart.getQuantities();

        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            int requestedQuantity = quantities.get(i);

            if (product.getStock() < requestedQuantity) {
                throw new OutOfStockException("Товара " + product.getName() + " недостаточно на складе.");
            }
        }
    }

    /**
     * Создает заказ из корзины и обновляет склад.
     *
     * @param cart текущая корзина пользователя
     * @return созданный заказ
     */
    public Order createOrderFromCart(Cart cart) {
        List<Product> products = cart.getProducts();
        List<Integer> quantities = cart.getQuantities();

        Order order = new Order();
        order.setUser(userService.getCurrentUser());
        order.setOrderDate(LocalDateTime.now());

        List<OrderItem> orderItems = new ArrayList<>();
        double totalAmount = 0.0;

        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            int requestedQuantity = quantities.get(i);

            product.setStock(product.getStock() - requestedQuantity);
            productService.saveProduct(product);

            double price = (product.getDiscountPrice() != null) ? product.getDiscountPrice() : product.getPrice();
            totalAmount += price * requestedQuantity;

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(requestedQuantity);
            orderItem.setPrice(price);
            orderItem.setOrder(order);
            orderItems.add(orderItem);

        }
        order.setItems(orderItems);
        order.setTotalAmount(totalAmount);
        saveOrder(order);

        return order;
    }

    /**
     * Отправляет письмо с подтверждением заказа.
     *
     * @param order заказ
     */
    public void sendOrderConfirmation(Order order) {
        String email = userService.getCurrentUser().getEmail();
        emailService.sendOrderConfirmation(email, order);
    }

    /**
     * Сохраняет заказ в базе данных.
     *
     * @param order заказ
     */
    public void saveOrder(Order order) {
        orderRepository.save(order);
    }
}

