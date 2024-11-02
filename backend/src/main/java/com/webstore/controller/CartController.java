package com.webstore.controller;

import com.webstore.model.Cart;
import com.webstore.dto.CartDTO;
import com.webstore.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/cart")
@PreAuthorize("isAuthenticated()")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping()
    public ResponseEntity<Cart> getCart() {
        Cart cart = cartService.getCart();
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addProductToCart(@RequestBody CartDTO request) {
        cartService.addProductToCart(request.getProductId(), request.getQuantity());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Void> removeProductFromCart(@RequestBody CartDTO request) {
        cartService.removeProductFromCart(request.getProductId());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update-quantity")
    public ResponseEntity<Void> updateProductQuantity(@RequestBody CartDTO request) {
        cartService.updateProductQuantity(request.getProductId(), request.getQuantity());
        return ResponseEntity.ok().build();
    }
}
