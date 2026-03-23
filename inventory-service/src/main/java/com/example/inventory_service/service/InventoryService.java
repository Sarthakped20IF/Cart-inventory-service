package com.example.inventory_service.service;

import com.example.inventory_service.dto.InventoryRequest;
import com.example.inventory_service.dto.InventoryResponse;
import com.example.inventory_service.entity.Inventory;
import com.example.inventory_service.repository.InventoryRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository repository;

    public InventoryResponse checkStock (InventoryRequest request) {
        log.info("checking stock for product id {}", request.getProductId());
        Inventory inventory = repository.findById(request.getProductId()).orElse(null);
        if (inventory == null) {
            log.info("fetching product ... ");
            return new InventoryResponse(false, inventory.getAvailableQuantity(), "Product Not Found");
        }
        else if (inventory.getAvailableQuantity()>= request.getQuantity()) {
            log.info("Stock is available ... ");
            return new InventoryResponse(true, inventory.getAvailableQuantity(), "Stock is Available");
        }

        return new InventoryResponse(false, inventory.getAvailableQuantity(), "Product Out of Stock! ");

    }

    @Transactional
    public InventoryResponse reduceStock (InventoryRequest request) {
       log.info("Updating Inventory of {}", request.getProductId());
        Inventory inventory = repository.findById(request.getProductId()).orElse(null);
        if (inventory == null) {
            log.info("fetching product details... ");
            throw new RuntimeException("Product Not Found");
        }
        if (inventory.getAvailableQuantity()<request.getQuantity()) {
            log.info("Insufficient stock ... ");
            return new InventoryResponse(false, inventory.getAvailableQuantity(), "Insufficient stock");
        }

        inventory.setAvailableQuantity(inventory.getAvailableQuantity() - request.getQuantity());
        repository.save(inventory);
        return new InventoryResponse(true, inventory.getAvailableQuantity(), "Inventory Updated");

    }

    @Transactional
    public void addStock (InventoryRequest request) {
        log.info("adding stock for product id {}", request.getProductId());
       Inventory inventory = repository.findById(request.getProductId()).orElse(null);
       if (inventory == null) {
           log.info("fetching product details... ");
           throw new RuntimeException("Product Not Found");
       }
        inventory.setAvailableQuantity(inventory.getAvailableQuantity() + request.getQuantity());
       repository.save(inventory);
    }

}
