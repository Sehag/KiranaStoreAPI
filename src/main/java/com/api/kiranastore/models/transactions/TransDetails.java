package com.api.kiranastore.models.transactions;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class TransDetails {
    private String transId;
    private double transAmount;
    private LocalDateTime transTime;
}
