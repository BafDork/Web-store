package com.webstore.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewRequestDTO {
    private Long productId;
    private String text;
    private int rating;
}