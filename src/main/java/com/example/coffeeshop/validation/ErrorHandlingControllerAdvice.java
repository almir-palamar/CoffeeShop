package com.example.coffeeshop.validation;

import com.example.coffeeshop.exceptions.MoreThan5PendingOrdersToGoException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ErrorHandlingControllerAdvice {

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

    /*
    * this exception is throws in Filter, so it cannot be caught with this handler
    * */

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

//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public ResponseEntity<Object> handleResponseEntityException(Exception ex, WebRequest request) {
//        return new ResponseEntity<>("An error occurred: " +  ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleHttpClientErrorException(AccessDeniedException ex) {
        return "You are not authorized for this action!";
    }

}
