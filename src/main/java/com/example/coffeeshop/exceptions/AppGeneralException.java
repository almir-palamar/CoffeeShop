package com.example.coffeeshop.exceptions;

import com.example.coffeeshop.dto.ResponseDTO;
import org.springframework.http.HttpStatus;

public class AppGeneralException extends RuntimeException{

    private final ResponseDTO responseDTO;

    public AppGeneralException(HttpStatus httpStatus, String message){
        this.responseDTO = new ResponseDTO(httpStatus, message);
    }

    public ResponseDTO getResponseBodyDTO() {
        return responseDTO;
    }
}
