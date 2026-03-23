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

    @CircuitBreaker(name = "inventoryService",fallbackMethod = "fallback")
    @Retry(name = "inventoryService")
    public InventoryResponse checkStock(InventoryRequest request){
        return inventoryClient.checkStock(request);
    }

// here -1 to avoid confusion between down and out of stock
    public InventoryResponse fallback(InventoryRequest request, Exception ex){
        return new InventoryResponse(false,-1,"inventory-service DOWN");
    }
}
