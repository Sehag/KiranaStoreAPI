package com.api.kiranastore.models.report;

import com.api.kiranastore.enums.HttpStatus;
import com.api.kiranastore.response.ApiResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReportResponse {
    private ApiResponse apiResponse;

    public ReportResponse(boolean success,Object data,int statusCode, String message, HttpStatus status){
        ApiResponse response = new ApiResponse();
        response.setSuccess(success);
        response.setData(data);
        response.setHttpStatusCode(statusCode);
        response.setStatus(status);
        response.setMessage(message);
        this.setApiResponse(response);
    }
}
