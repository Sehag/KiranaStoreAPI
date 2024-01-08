package com.api.kiranastore.entities;

import com.api.kiranastore.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("transactions")
@Data
public class Transactions {

    @Id private String id;
    private String userName;
    private String transType;
    private double amount;
    private LocalDateTime transTime;
    private String currency;
}
