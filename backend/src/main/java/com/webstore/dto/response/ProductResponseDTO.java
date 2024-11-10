package com.webstore.dto.response;

import com.webstore.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductResponseDTO {
    private Long id;
    private String name;
    private String description;
    private double price;
    private Double discountPrice;
    private int stock;
    private String imageUrl;

    public ProductResponseDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.discountPrice = product.getDiscountPrice();
        this.stock = product.getStock();
        this.imageUrl = product.getImageUrl();
    }
}

