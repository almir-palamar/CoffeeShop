package com.example.coffeeshop.exceptions;

import com.example.coffeeshop.dto.ResponseBodyDTO;
import org.springframework.http.HttpStatus;

public class AppGeneralException extends RuntimeException{

    private final ResponseBodyDTO responseBodyDTO;

    public AppGeneralException(HttpStatus httpStatus, String message){
        this.responseBodyDTO = new ResponseBodyDTO(httpStatus, message);
    }

    public ResponseBodyDTO getResponseBodyDTO() {
        return responseBodyDTO;
    }
}
