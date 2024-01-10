package com.api.kiranastore.models.transactions;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransDetails {
    private String transId;
    private double transAmount;
    private LocalDateTime transTime;
}
