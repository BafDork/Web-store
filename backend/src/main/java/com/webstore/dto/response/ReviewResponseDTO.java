package com.webstore.dto.response;

import com.webstore.model.Review;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ReviewResponseDTO {
    private Long id;
    private String author;
    private String text;
    private int rating;
    private LocalDateTime createdAt;

    public ReviewResponseDTO(Review review) {
        this.id = review.getId();
        this.author = review.getUser().getFirstName() + " " + review.getUser().getLastName();
        this.text = review.getComment();
        this.rating = review.getRating();
        this.createdAt = review.getCreatedAt();
    }
}