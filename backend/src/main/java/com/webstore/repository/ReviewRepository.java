package com.webstore.repository;

import com.webstore.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    /**
     * Поиск отзывов по ID продукта и сортировка по дате создания.
     *
     * @param productId ID продукта
     * @return список отзывов
     */
    List<Review> findByProductIdOrderByCreatedAtDesc(Long productId);
}