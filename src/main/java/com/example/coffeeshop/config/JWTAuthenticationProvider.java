package com.example.coffeeshop.config;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class JWTAuthenticationProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // define jwt decoder of token
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }




}
