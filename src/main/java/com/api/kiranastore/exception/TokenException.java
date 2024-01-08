package com.api.kiranastore.exception;

import com.api.kiranastore.response.ApiResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.el.parser.Token;

@Data
@EqualsAndHashCode(callSuper = true)
public class TokenException extends RuntimeException{
    private ApiResponse apiResponse;

    public TokenException(String error, String errorMessage, String code){
        ApiResponse response = new ApiResponse();
        response.setErrorCode(code);
        response.setErrorMessage(errorMessage);
        response.setError(error);
        this.setApiResponse(response);
    }
}
