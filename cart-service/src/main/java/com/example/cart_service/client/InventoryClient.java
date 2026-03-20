package com.example.cart_service.client;

import com.example.cart_service.dto.InventoryRequest;
import com.example.cart_service.dto.InventoryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "inventory-service",url = "http://localhost:8082")
public interface InventoryClient {
    @PostMapping("/inventory/check")
    InventoryResponse checkStock(@RequestBody InventoryRequest request);
}
