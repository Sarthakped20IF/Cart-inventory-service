package com.example.inventory_service.dto;

import com.example.inventory_service.entity.Inventory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryResponse {
    private boolean available;
    private int availableQuantity;
    private String message;
}
