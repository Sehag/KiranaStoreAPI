package com.api.kiranastore.controllers;

import com.api.kiranastore.models.transactions.TransRequest;
import com.api.kiranastore.response.ApiResponse;
import com.api.kiranastore.services.transactions.TransServicesImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/makePayment")
public class PaymentController {
    private final TransServicesImpl transService;

    public PaymentController(TransServicesImpl transService) {
        this.transService = transService;
    }

    /**
     * Make a transaction
     *
     * @param transRequest consists of trans amount
     * @param jwtToken access token
     */
    @PostMapping()
    public ResponseEntity<ApiResponse> makePayment(
            @RequestBody TransRequest transRequest,
            @RequestHeader("Authorization") String jwtToken) {
        ApiResponse apiResponse = transService.makePayment(jwtToken, transRequest);
        return ResponseEntity.ok().body(apiResponse);
    }
}
