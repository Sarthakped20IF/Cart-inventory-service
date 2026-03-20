package com.example.cart_service.service;

import com.example.cart_service.dto.AddToCartRequest;
import com.example.cart_service.dto.InventoryRequest;
import com.example.cart_service.dto.InventoryResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {
    private final InventoryServiceClient inventoryServiceClient;


    public String addToCart(AddToCartRequest request){
        log.info("request reccieved : {}",request);

        InventoryResponse response = inventoryServiceClient.checkStock(
                new InventoryRequest(request.getProductId(), request.getQuantity()));
//      if stock is not available then throw expection
        if(!response.isAvailable()){
            log.error("{} not available",response);
            throw new RuntimeException(response.getMessage());
        }
//        setup commerce tools connection here


        return "Item added to cart";
    }
}

