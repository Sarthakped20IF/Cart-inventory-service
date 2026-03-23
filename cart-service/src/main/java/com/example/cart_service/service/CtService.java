package com.example.cart_service.service;

import com.example.cart_service.config.CommerceToolsConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
@RequiredArgsConstructor
public class CtService {
    private final CommerceToolsConfig ctconfig;
    private final AuthService authService;
    private final WebClient webClient;

    public String createCart(){
        String token = authService.getAccessToken();
        String body = """
                {
                "currency":"USD"
                }
                """;
        return webClient.post()
                .uri(ctconfig.getApiUrl()+"/"+ctconfig.getProjectKey()+"/carts")
                .headers(headers -> headers.setBearerAuth(token) )
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String addItemToCart(String cartId , int version,String productId,int quantity ){
        String token = authService.getAccessToken();

        String body = """
                {
                    "version" : %d,
                    "actions":[
                        {
                           "action": "addLineItem",
                           "productId": "%s",
                           "variantId": 1,
                           "quantity": %d
                        }
                    ]
                }    
                """.formatted(version,productId,quantity);
        return webClient.post()
                .uri(ctconfig.getApiUrl()+"/"+ctconfig.getProjectKey()+"/carts/"+cartId)
                .headers(headers->headers.setBearerAuth(token))
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
