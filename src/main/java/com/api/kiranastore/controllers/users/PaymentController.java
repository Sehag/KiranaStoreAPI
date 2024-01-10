package com.api.kiranastore.controllers.users;

import com.api.kiranastore.models.transactions.TransactionResponse;
import com.api.kiranastore.requests.PaymentRequest;
import com.api.kiranastore.services.transactions.TransactionServices;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/user/makePayment")
@PreAuthorize("hasAuthority('USER')")
public class PaymentController {
    private final TransactionServices transService;

    public PaymentController(TransactionServices transService) {
        this.transService = transService;
    }

    @PostMapping()
    public ResponseEntity<TransactionResponse> makePayment(@RequestBody PaymentRequest payReq, @RequestHeader("Authorization") String jwtToken){
        TransactionResponse transResponse = transService.makePayment(jwtToken,payReq.getAmount());
        return ResponseEntity.ok().body(transResponse);
    }
}
