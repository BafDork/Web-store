package com.webstore.service.unit;

import com.webstore.dto.request.ReviewRequestDTO;
import com.webstore.dto.response.ReviewResponseDTO;
import com.webstore.exceptions.ResourceNotFoundException;
import com.webstore.model.Product;
import com.webstore.model.Review;
import com.webstore.model.User;
import com.webstore.repository.ProductRepository;
import com.webstore.repository.ReviewRepository;
import com.webstore.service.ReviewService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.any;

public class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ReviewService reviewService;

    private Product product;
    private User user;
    private ReviewRequestDTO reviewRequest;
    private AutoCloseable mocks;

    @BeforeEach
    public void setUp() {
        mocks = MockitoAnnotations.openMocks(this);

        product = new Product();
        product.setId(1L);
        product.setName("Test Product");

        user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");

        reviewRequest = new ReviewRequestDTO(1L, "Great product!", 5);
    }

    @AfterEach
    void tearDown() throws Exception {
        if (mocks != null) {
            mocks.close();
        }
    }

    @Test
    public void testAddReview_ProductFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(reviewRepository.save(any(Review.class))).thenReturn(new Review());

        reviewService.addReview(reviewRequest, user);

        verify(reviewRepository, times(1)).save(any(Review.class));
    }

    @Test
    public void testAddReview_ProductNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> reviewService.addReview(reviewRequest, user));
    }

    @Test
    public void testGetReviewsByProduct() {
        Review review1 = new Review();
        review1.setProduct(product);
        review1.setUser(user);
        review1.setComment("Great product!");
        review1.setRating(5);
        review1.setCreatedAt(LocalDateTime.now());

        Review review2 = new Review();
        review2.setProduct(product);
        review2.setUser(user);
        review2.setComment("Good quality.");
        review2.setRating(4);
        review2.setCreatedAt(LocalDateTime.now().minusDays(1));

        when(reviewRepository.findByProductIdOrderByCreatedAtDesc(1L))
                .thenReturn(Arrays.asList(review1, review2));

        List<ReviewResponseDTO> reviews = reviewService.getReviewsByProduct(1L);

        assertNotNull(reviews);
        assertEquals(2, reviews.size());
    }

    @Test
    public void testGetReviewsByProduct_NoReviews() {
        when(reviewRepository.findByProductIdOrderByCreatedAtDesc(1L)).thenReturn(Collections.emptyList());

        List<ReviewResponseDTO> reviews = reviewService.getReviewsByProduct(1L);

        assertNotNull(reviews);
        assertTrue(reviews.isEmpty());
    }
}
