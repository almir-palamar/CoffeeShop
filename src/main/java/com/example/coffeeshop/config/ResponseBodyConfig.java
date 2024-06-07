package com.example.coffeeshop.config;

import com.example.coffeeshop.dto.ResponseDTO;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/*
 * Currently unnecessary configuration as everything is being handled
 * in @ControllerAdvice ExceptionHandlingControllerAdvice
 */

@ControllerAdvice
public class ResponseBodyConfig implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(@NonNull MethodParameter returnType, @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  @NonNull MethodParameter returnType,
                                  @NonNull MediaType selectedContentType,
                                  @NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  @NonNull ServerHttpRequest request,
                                  @NonNull ServerHttpResponse response) {

        if (!(body instanceof ResponseDTO)) {
            return new ResponseDTO(
                    HttpStatus.OK,
                    "Success",
                    body
            );
        }

        return body;
    }
}
