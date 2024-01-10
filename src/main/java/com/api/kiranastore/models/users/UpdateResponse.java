package com.api.kiranastore.models.users;

import com.api.kiranastore.enums.HttpStatus;
import com.api.kiranastore.response.ApiResponse;
import lombok.Data;

@Data
public class UpdateResponse {
    private ApiResponse apiResponse;

    public UpdateResponse(boolean success,Object data,String statusCode, String message, HttpStatus status){
        ApiResponse response = new ApiResponse();
        response.setSuccess(success);
        response.setData(data);
        response.setHttpStatusCode(statusCode);
        response.setStatus(status);
        response.setMessage(message);
        this.setApiResponse(response);
    }
}
