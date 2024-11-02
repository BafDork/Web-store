package com.webstore.dto;

import com.webstore.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private double price;
    private Double discountPrice;
    private int stock;
    private String imageUrl;

    public ProductDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.discountPrice = product.getDiscountPrice();
        this.stock = product.getStock();
        this.imageUrl = product.getImageUrl();
    }
}

