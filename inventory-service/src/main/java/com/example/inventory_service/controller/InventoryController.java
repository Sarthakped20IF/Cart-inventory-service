package com.example.inventory_service.controller;

import com.example.inventory_service.dto.InventoryRequest;
import com.example.inventory_service.dto.InventoryResponse;
import com.example.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService service;

    @GetMapping("/check-inventory")
    public ResponseEntity<InventoryResponse> checkInventory(@RequestBody InventoryRequest request) {
        log.info("Checking Inventory...");
        return ResponseEntity.ok(service.checkStock(request));
    }

    @PostMapping("/reduce-stock")
    public  ResponseEntity<InventoryResponse> reduceStock(@RequestBody InventoryRequest request) {
        log.info("Updating Stock...");

        return ResponseEntity.ok(service.reduceStock(request));
    }

    @PostMapping("/add-stock")
    public void addStock(@RequestBody InventoryRequest request) {
        log.info("Adding Stock...");
        service.addStock(request);

    }
}
