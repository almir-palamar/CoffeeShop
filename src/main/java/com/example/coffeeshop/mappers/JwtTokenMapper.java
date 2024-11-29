package com.example.coffeeshop.mappers;

import com.example.coffeeshop.dto.auth.JwtTokenDTO;
import com.example.coffeeshop.models.JwtToken;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenMapper {

    public JwtTokenDTO toJwtTokenDTO(JwtToken jwtToken) {
        return new JwtTokenDTO(
                jwtToken.getToken()
        );
    }

}
