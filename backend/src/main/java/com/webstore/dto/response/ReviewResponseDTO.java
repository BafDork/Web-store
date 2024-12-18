package com.webstore.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class ReviewResponseDTO {
    private Long id;
    private String author;
    private String text;
    private int rating;
    private LocalDateTime createdAt;
    private List<Long> photos;
}