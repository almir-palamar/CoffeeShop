package com.example.coffeeshop.validation;

import com.example.coffeeshop.dto.JSONResponse;
import com.example.coffeeshop.exceptions.MoreThan5PendingOrdersToGoException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ErrorHandlingControllerAdvice implements ResponseBodyAdvice<Object> {

    /**
     * MethodArgumentNotValidException -> @RequestBody ; class -> / ; method -> @Valid
     * ConstraintValidationException -> @PathVariable, @RequestParam ; class -> @Validated; method -> constraint annotations
     **/

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorResponse handleConstraintViolationException(ConstraintViolationException ex) {
        List<Violation> violations = new ArrayList<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            violations.add(
                    new Violation(
                            violation.getPropertyPath().toString(),
                            violation.getMessage()
                    )
            );
        }
        return new ValidationErrorResponse(violations, "Error", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<Violation> violations = new ArrayList<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            violations.add(
                    new Violation(
                            fieldError.getField(),
                            fieldError.getDefaultMessage()
                    )
            );
        }
        return new ValidationErrorResponse(violations, "Error", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MoreThan5PendingOrdersToGoException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorResponse handleMoreThan5PendingOrdersToGoException(MoreThan5PendingOrdersToGoException ex) {
        List<Violation> violations = new ArrayList<>();
        violations.add(
                new Violation("Order", ex.getMessage())
        );
        return new ValidationErrorResponse(violations, "Error", HttpStatus.BAD_REQUEST);
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {

        JSONResponse jsonResponse = new JSONResponse(
          200,
          "Success",
          body
        );

        return jsonResponse;

    }
}
