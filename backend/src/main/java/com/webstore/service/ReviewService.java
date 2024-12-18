package com.webstore.service;

import com.webstore.dto.request.ReviewRequestDTO;
import com.webstore.dto.response.ReviewResponseDTO;
import com.webstore.exceptions.ResourceNotFoundException;
import com.webstore.model.Product;
import com.webstore.model.Review;
import com.webstore.model.ReviewPhoto;
import com.webstore.model.User;
import com.webstore.repository.ProductRepository;
import com.webstore.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;

    /**
     * Возвращает отзыв по его ID.
     *
     * @param reviewId идентификатор отзыва
     * @return объект отзыва
     * @throws ResourceNotFoundException если отзыв с указанным ID не найден
     */
    public Review getReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Отзыв не найден"));
    }

    /**
     * Добавление отзыва к продукту.
     *
     * @param request данные отзыва
     * @param user    пользователь, оставивший отзыв
     */
    public Review addReview(ReviewRequestDTO request, User user) {
        Long productId = request.getProductId();
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Продукт не найден с ID: " + productId));

        var review = Review.builder()
                .product(product)
                .user(user)
                .comment(request.getText())
                .rating(request.getRating())
                .createdAt(LocalDateTime.now())
                .build();

        return reviewRepository.save(review);
    }

    /**
     * Получение всех отзывов к продукту.
     *
     * @param productId ID продукта
     * @return список DTO объектов для отзывов
     */
    public List<ReviewResponseDTO> getReviewsByProduct(Long productId) {
        return reviewRepository.findByProductIdOrderByCreatedAtDesc(productId).stream()
                .map(review -> new ReviewResponseDTO(
                        review.getId(),
                        review.getUser().getFirstName() + " " + review.getUser().getLastName(),
                        review.getComment(),
                        review.getRating(),
                        review.getCreatedAt(),
                        review.getPhotos().stream()
                                .map(ReviewPhoto::getId)
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }

    /**
     * Удаление отзыва по Id.
     *
     * @param reviewId Id отзыва для удаления
     */
    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }

}
