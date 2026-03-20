package com.example.cart_service.service;

import com.example.cart_service.client.InventoryClient;
import com.example.cart_service.dto.InventoryRequest;
import com.example.cart_service.dto.InventoryResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class InventoryServiceClient {
    private final InventoryClient inventoryClient;

    @CircuitBreaker(name = "inventory-service",fallbackMethod = "fallback")
    @Retry(name = "inventory-service")
    public InventoryResponse checkStock(@RequestBody InventoryRequest request){
        return inventoryClient.checkStock(request);
    }


    public InventoryResponse fallback(InventoryRequest request, Throwable ex){
        return new InventoryResponse(false,0,"inventory-service unavailable");
    }
}
