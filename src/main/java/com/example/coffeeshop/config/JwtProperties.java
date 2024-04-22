package com.example.coffeeshop.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt.token")
public class JwtProperties {

    private Long expiration;
    private String secret;

    public void setExpiration(Long expiration) {
        this.expiration = expiration;
    }

    public Long getExpiration() {
        return expiration;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getSecret() {
        return secret;
    }

}
