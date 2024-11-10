package com.webstore.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartProductRequestDTO {
    private Long productId;
    private int quantity;
}


