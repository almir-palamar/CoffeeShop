package com.example.coffeeshop.exceptions;

import com.example.coffeeshop.dto.ResponseBodyDTO;
import com.example.coffeeshop.validation.ValidationErrorResponse;
import com.example.coffeeshop.validation.Violation;
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

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ExceptionHandlingControllerAdvice {

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

    /*
    * None of the exceptions below is not getting caught
    */

//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public ResponseEntity<Object> handleResponseEntityException(Exception ex, WebRequest request) {
//        return new ResponseEntity<>("An error occurred: " +  ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//    }

//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.FORBIDDEN)
//    public String handleAccessDeniedException(AccessDeniedException ex) {
//        return "You are not authorized for this action!";
//    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ResponseBodyDTO> handleEntityNotFoundException(EntityNotFoundException ex) {
        ResponseBodyDTO responseBodyDTO = new ResponseBodyDTO(ex.getResponseBodyDTO().getStatus(), ex.getResponseBodyDTO().getMessage() , null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBodyDTO);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ResponseBodyDTO> handleBadCredentialsException(BadCredentialsException ex) {
        ResponseBodyDTO responseBodyDTO = new ResponseBodyDTO(ex.getResponseBodyDTO().getStatus(), ex.getResponseBodyDTO().getMessage() , null);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBodyDTO);
    }

}
