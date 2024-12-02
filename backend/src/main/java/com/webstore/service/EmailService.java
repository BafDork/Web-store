package com.webstore.service;

import com.webstore.model.Order;
import com.webstore.model.OrderItem;
import com.webstore.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Value("${spring.mail.username}")
    private String mailFrom;
    private final JavaMailSender mailSender;

    /**
     * Отправляет письмо с подтверждением заказа пользователю.
     * Формирует тело письма с информацией о заказе и отправляет его на указанный email.
     *
     * @param userEmail email пользователя для отправки письма
     * @param order заказ, информацию о котором нужно отправить
     */
    public void sendOrderConfirmation(String userEmail, Order order) {
        String subject = "Подтверждение вашего заказа №" + order.getId();
        StringBuilder messageContent = new StringBuilder();

        messageContent.append("<p>Дата заказа: ").append(order.getOrderDate()).append("</p>");
        messageContent.append("<h3>Состав заказа:</h3>");
        messageContent.append("<ul>");

        for (OrderItem item : order.getItems()) {
            Product product = item.getProduct();
            messageContent
                    .append("<li>")
                    .append(product.getName())
                    .append(" (x")
                    .append(item.getQuantity())
                    .append(") - ")
                    .append(product.getPrice() * item.getQuantity())
                    .append(" руб.</li>");
        }

        messageContent.append("</ul>");
        messageContent.append("<p>Общая сумма заказа: ").append(order.getTotalAmount()).append(" руб.</p>");
        messageContent.append("<p>Спасибо за покупку!</p>");

        try {
            sendEmail(userEmail, subject, messageContent.toString());
        } catch (MailException | MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException("Ошибка при отправке письма", e);
        }
    }

    /**
     * Формирует и отправляет email-сообщение.
     *
     * @param to email получателя
     * @param subject тема письма
     * @param content содержание письма
     * @throws MailException если не удается отправить письмо
     * @throws MessagingException если произошла ошибка при формировании письма
     */
    private void sendEmail(String to, String subject, String content) throws MailException, MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setFrom(mailFrom);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true);

        mailSender.send(mimeMessage);
    }
}
