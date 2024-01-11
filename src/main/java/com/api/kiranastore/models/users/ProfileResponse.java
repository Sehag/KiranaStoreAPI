package com.api.kiranastore.models.users;

import com.api.kiranastore.enums.HttpStatus;
import com.api.kiranastore.response.ApiResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileResponse {
    private ApiResponse apiResponse;

    public ProfileResponse(
            boolean success, Object data, int statusCode, String message, HttpStatus status) {
        ApiResponse response = new ApiResponse();
        response.setSuccess(success);
        response.setData(data);
        response.setHttpStatusCode(statusCode);
        response.setStatus(status);
        response.setMessage(message);
        this.setApiResponse(response);
    }
}
