package com.example.cart_service.controller;

import com.example.cart_service.dto.AddToCartRequest;
import com.example.cart_service.dto.InventoryRequest;
import com.example.cart_service.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<?>addToCart(@RequestBody AddToCartRequest request){
        return ResponseEntity.ok(cartService.addToCart(request));
    }
}
