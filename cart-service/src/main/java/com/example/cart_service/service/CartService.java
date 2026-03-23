package com.example.cart_service.service;

import com.example.cart_service.config.CommerceToolsConfig;
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
    private final CtService ctService;
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
        if(response.getAvailableQuantity() == -1){
            throw new RuntimeException("Inventory service down");
        }
//        setup commerce tools connection here

//        create cart
        String CartResponse = ctService.createCart();
        String cartId = extractCartId(CartResponse);
        log.info("cartId: {}",cartId);
        int version = extractVersion(CartResponse);
        log.info("version: {}",version);

//        Add Line item in cart
        return ctService.addItemToCart(cartId,version,request.getProductId(),request.getQuantity());
    }

    private String extractCartId(String CartResponse){
        return CartResponse.split("\"id\":\"")[1].split("\"")[0];
    }
    private int extractVersion(String CartResponse){
        return Integer.parseInt(CartResponse.split("\"version\":")[1].split(",")[0]);
    }
}

