package com.api.kiranastore.response;

import com.api.kiranastore.enums.HttpStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {

    private boolean success = true;
    private Object data;
    private Object view;
    private HttpStatus status;
    private Object message;
    private String httpStatusCode;
    private String displayMsg;

}