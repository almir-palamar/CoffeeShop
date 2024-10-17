package com.example.coffeeshop.config;

import com.example.coffeeshop.dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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
 *
 * 15.10.2024 => This is something that is being called for formatting responses.
 *
 */

/*
 * Like this Config class is invoked when the response is going towards user.
 * Response gets in this class before going back to appropriate controller
 * And If controller expects to return the String it could not do so
 * Because this part would return ResponseDTO objects' instance
 */

@ControllerAdvice
public class ResponseBodyConfig implements ResponseBodyAdvice<Object> {

    private final MessageSource messageSource;

    /* As the class have only one constructor no need to @Autowire.
     * When there is more than 1 constructor using @Autowire
     * will tell Spring Boot which constructor to use for dependency injection
     */
    public ResponseBodyConfig(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

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
        return formatResponse(body); //with this swagger wont work
    }

    private Object formatResponse(Object body) {
        if (body instanceof ResponseDTO responseDTO) {
            responseDTO.setMessage(
                    messageSource.getMessage(responseDTO.getMessage(), null, LocaleContextHolder.getLocale()));
            return responseDTO;
        }
        return new ResponseDTO(
                HttpStatus.OK,
                messageSource.getMessage("success", null, LocaleContextHolder.getLocale()),
                body
        );
    }
}
