package com.webstore.repository;

import com.webstore.model.ReviewPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewPhotoRepository extends JpaRepository<ReviewPhoto, Long> {

    /**
     * Поиск фотографии отзыва по ID отзыва.
     *
     * @param reviewId ID отзыва
     * @return объект фотографии отзыва
     */
    Optional<ReviewPhoto> findByReviewId(Long reviewId);
}
