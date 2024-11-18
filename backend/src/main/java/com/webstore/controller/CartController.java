package com.webstore.controller;

import com.webstore.dto.response.CartProductResponseDTO;
import com.webstore.model.Cart;
import com.webstore.dto.request.CartProductRequestDTO;
import com.webstore.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/cart")
@PreAuthorize("isAuthenticated()")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<List<CartProductResponseDTO>> getCartProducts() {
        List<CartProductResponseDTO> cartProducts = cartService.getCartProducts();
        return ResponseEntity.ok(cartProducts);
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addProductToCart(@RequestBody CartProductRequestDTO request) {
        cartService.addProductToCart(request.getProductId(), request.getQuantity());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<Void> removeProductFromCart(@PathVariable Long productId) {
        cartService.removeProductFromCart(productId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update-quantity")
    public ResponseEntity<Void> updateProductQuantity(@RequestBody CartProductRequestDTO request) {
        cartService.updateProductQuantity(request.getProductId(), request.getQuantity());
        return ResponseEntity.ok().build();
    }

}
