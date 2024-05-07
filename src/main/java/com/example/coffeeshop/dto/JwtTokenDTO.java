package com.example.coffeeshop.dto;

public class JwtTokenDTO {

    private String accessToken;

    public JwtTokenDTO() {}

    public JwtTokenDTO(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
