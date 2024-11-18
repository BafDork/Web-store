package com.webstore.repository;

import com.webstore.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    /**
     * Получить список заказов пользователя.
     *
     * @param userId идентификатор пользователя
     * @return список заказов пользователя
     */
    List<Order> findByUserId(Long userId);
}