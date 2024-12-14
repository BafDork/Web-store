package com.webstore.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ProductRequestDTO {
    private String name;
    private String description;
    private double price;
    private Double discountPrice;
    private int stock;
    private String imageUrl;
    private List<Long> categoryIds;
}