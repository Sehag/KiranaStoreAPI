package com.api.kiranastore.models.transactions;

import lombok.Data;

@Data
public class TransactionResponse {
    private String transId;
    private double transAmount;
    private String message;
}
