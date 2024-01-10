package com.api.kiranastore.exception;

import com.api.kiranastore.enums.HttpStatus;
import com.api.kiranastore.response.ApiResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TokenException extends RuntimeException{

    private ApiResponse apiResponse;

    public TokenException(HttpStatus status, String statusMessage, String code){
        ApiResponse response = new ApiResponse();
        response.setHttpStatusCode(code);
        response.setMessage(statusMessage);
        response.setStatus(status);
        this.setApiResponse(response);
    }
}
