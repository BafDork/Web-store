package com.webstore.repository;

import com.webstore.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    /**
     * Поиск корзины по идентификатору пользователя.
     *
     * @param userId идентификатор пользователя
     * @return объект корзины
     */
    Cart findByUserId(Long userId);
}
