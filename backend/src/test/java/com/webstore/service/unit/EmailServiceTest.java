package com.webstore.service.unit;

import com.webstore.model.Order;
import com.webstore.model.OrderItem;
import com.webstore.model.Product;
import com.webstore.service.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.util.ReflectionTestUtils;

import javax.mail.internet.MimeMessage;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(emailService, "mailFrom", "noreply@example.com");
    }

    /**
     * Тестирует отправку подтверждения заказа.
     * Ожидается, что метод sendEmail будет вызван с корректными параметрами.
     */
    @Test
    void sendOrderConfirmation_ShouldSendEmail_WhenValidOrder() {
        Product product = new Product();
        product.setName("Test Product");
        product.setPrice(100.0);

        OrderItem item = new OrderItem();
        item.setProduct(product);
        item.setQuantity(2);

        Order order = new Order();
        order.setId(1L);
        order.setOrderDate(LocalDateTime.of(2024, 11, 25, 12, 0));
        order.setItems(Collections.singletonList(item));
        order.setTotalAmount(200.0);

        String userEmail = "test@example.com";

        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

        emailService.sendOrderConfirmation(userEmail, order);

        verify(mailSender, times(1)).send(mimeMessage);

        ArgumentCaptor<MimeMessage> captor = ArgumentCaptor.forClass(MimeMessage.class);
        verify(mailSender).send(captor.capture());
        MimeMessage sentMessage = captor.getValue();

        assertNotNull(sentMessage);
        verify(mailSender).send(sentMessage);
    }

    /**
     * Тестирует обработку исключения при отправке письма.
     * Ожидается, что будет выброшено RuntimeException при ошибке.
     */
    @Test
    void sendOrderConfirmation_ShouldThrowException_WhenMailErrorOccurs() {
        Order order = new Order();
        order.setId(1L);
        order.setTotalAmount(200.0);

        String userEmail = "test@example.com";

        when(mailSender.createMimeMessage()).thenThrow(new MailException("Ошибка отправки письма") {
        });

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> emailService.sendOrderConfirmation(userEmail, order));

        assertTrue(exception.getMessage().contains("Ошибка при отправке письма"));
        verify(mailSender, times(1)).createMimeMessage();
    }
}
