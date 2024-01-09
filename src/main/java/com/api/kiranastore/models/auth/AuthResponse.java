package com.api.kiranastore.models.auth;

import com.api.kiranastore.response.ApiResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthResponse {
    private ApiResponse apiResponse;

    public AuthResponse(boolean success,Object data,String statusCode, String message, String status){
        ApiResponse response = new ApiResponse();
        response.setSuccess(true);
        response.setData(data);
        response.setHttpStatusCode(statusCode);
        response.setStatus(status);
        response.setMessage(message);
        this.setApiResponse(response);
    }
}
