package com.example.inventory_service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
public class InventoryRequest {
    private String productId;
    private int quantity;
}
