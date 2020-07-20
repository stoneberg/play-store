package com.stone.store.play.shop.controller;

import com.stone.store.play.shop.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ShopController {

    private final ShopService shopService;


    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        return ResponseEntity.ok(shopService.getAllProducts());
    }

}