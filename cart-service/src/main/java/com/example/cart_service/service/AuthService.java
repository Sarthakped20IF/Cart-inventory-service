package com.example.cart_service.service;

import com.example.cart_service.config.CommerceToolsConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final CommerceToolsConfig CtConfig;
    private final WebClient webClient;


    public String getAccessToken(){
        log.info("Fetching Commerce Tools access token ");
        String response = webClient.post()
                .uri(CtConfig.getAuthUrl()+"/oauth/token")
                .headers(headers->headers.setBasicAuth(
                        CtConfig.getClientId(),CtConfig.getClientSecret()
                ))
                .bodyValue("grant_type=Client_credentials")
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return extractToken(response);
    }

    private String extractToken(String response) {
        return response.split("\"access_token\":\"")[1].split("\"")[0];
    }
}
