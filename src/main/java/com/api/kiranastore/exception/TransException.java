package com.api.kiranastore.exception;

import com.api.kiranastore.enums.HttpStatus;
import com.api.kiranastore.response.ApiResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransException extends RuntimeException {

    private ApiResponse apiResponse;

    public TransException(
            boolean success,
            HttpStatus status,
            Object data,
            String statusMessage,
            int httpStatusCode) {
        ApiResponse response = new ApiResponse();
        response.setSuccess(success);
        response.setHttpStatusCode(httpStatusCode);
        response.setData(data);
        response.setMessage(statusMessage);
        response.setStatus(status);
        this.setApiResponse(response);
    }
}
