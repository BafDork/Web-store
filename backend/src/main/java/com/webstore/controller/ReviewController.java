package com.webstore.controller;

import com.webstore.dto.request.ReviewRequestDTO;
import com.webstore.dto.response.ReviewResponseDTO;
import com.webstore.model.Review;
import com.webstore.model.User;
import com.webstore.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;


    @PostMapping("/api/review")
    public ResponseEntity<?> addReview(@RequestBody ReviewRequestDTO request, @AuthenticationPrincipal User user) {
        Review review = reviewService.addReview(request, user);
        return ResponseEntity.ok(review);
    }

    @GetMapping("/api/review/{productId}")
    public ResponseEntity<List<ReviewResponseDTO>> getReviews(@PathVariable Long productId) {
        List<ReviewResponseDTO> reviews = reviewService.getReviewsByProduct(productId);
        return ResponseEntity.ok(reviews);
    }

    @DeleteMapping("admin/review/delete/{reviewId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.ok("Отзыв удалён");
    }
}
