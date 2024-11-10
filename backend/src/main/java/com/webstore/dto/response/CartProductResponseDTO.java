package com.webstore.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartProductResponseDTO {
    private ProductResponseDTO product;
    private int quantity;
}