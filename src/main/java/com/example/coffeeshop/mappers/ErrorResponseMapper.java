package com.example.coffeeshop.mappers;

import com.example.coffeeshop.dto.JSONResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ErrorResponseMapper {

    public JSONResponse toJSON(HttpServletResponse httpServletResponse) {

        JSONResponse response = new JSONResponse(
                httpServletResponse.getStatus(),
                "Please wait...",
                null
        );

        return response;
    }

}
