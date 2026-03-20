package com.example.cart_service.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "commercetools")
@Data
public class CommerceToolsConfig {
    private String projectKey;
    private String clientId;
    private String clientSecret;
    private String apiUrl;
    private String authUrl;
}
