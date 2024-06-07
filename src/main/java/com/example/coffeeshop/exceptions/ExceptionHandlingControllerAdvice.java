package com.example.coffeeshop.exceptions;

import com.example.coffeeshop.dto.ErrorDTO;
import com.example.coffeeshop.dto.ResponseDTO;
import com.example.coffeeshop.validation.ValidationErrorResponse;
import com.example.coffeeshop.validation.Violation;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
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

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ResponseDTO> handleEntityNotFoundException(EntityNotFoundException ex) {
        ResponseDTO responseDTO = new ResponseDTO(ex.getResponseBodyDTO().getStatus(), ex.getResponseBodyDTO().getMessage() , null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorDTO> handleUnauthorizedException(UnauthorizedException ex) {
        ErrorDTO ErrorDTO = new ErrorDTO(ex.getResponseBodyDTO().getStatus(), ex.getResponseBodyDTO().getMessage() , null);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorDTO);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO> handleOtherExceptions(Exception ex) {
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage() , ex.getStackTrace());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
    }

    @ExceptionHandler(MoreThan5PendingOrdersToGoException.class)
    public ResponseEntity<ResponseDTO> handleOtherExceptions(MoreThan5PendingOrdersToGoException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getResponseBodyDTO());
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ResponseDTO> handleJwtExpiredException(ExpiredJwtException ex) {
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.UNAUTHORIZED, ex.getLocalizedMessage() , null);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseDTO);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ResponseDTO> handleAccessDeniedException(AccessDeniedException ex) {
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.UNAUTHORIZED, "You are not authorized for requested action." , null);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseDTO);
    }

}
