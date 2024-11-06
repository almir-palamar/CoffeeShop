package com.example.coffeeshop.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI coffeeShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Coffee Shop API").version("v1.0"))
                .components(new Components().addSecuritySchemes("JWTAuth", createJWTSecurityScheme()));
    }

    @Bean
    public SecurityScheme createJWTSecurityScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .name("JWTAuth")
                .scheme("bearer")
                .bearerFormat("JW")
                .in(SecurityScheme.In.HEADER);
    }
}