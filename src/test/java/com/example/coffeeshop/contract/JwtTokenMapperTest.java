package com.example.coffeeshop.contract;

import com.example.coffeeshop.dto.auth.JwtTokenDTO;
import com.example.coffeeshop.mappers.JwtTokenMapper;
import com.example.coffeeshop.models.JwtToken;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class JwtTokenMapperTest {

    @InjectMocks
    private JwtTokenMapper jwtTokenMapper;

    @Test
    void shouldReturnJwtTokenDTO() {
        JwtToken jwtToken = JwtToken.builder()
                .token("test-token")
                .revoked(false)
                .build();

        JwtTokenDTO jwtTokenDTO = jwtTokenMapper.toJwtTokenDTO(jwtToken);

        assertThat(jwtTokenDTO.accessToken()).isEqualTo(jwtToken.getToken());
    }
}