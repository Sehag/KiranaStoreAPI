package com.api.kiranastore.entities;

import com.api.kiranastore.enums.Currency;
import com.api.kiranastore.enums.TransType;
import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("transactions")
@Data
public class Transactions {

    @Id private String id;
    private String userName;
    private TransType transType;
    private double amount;
    private LocalDateTime transTime;
    private Currency currency;
}
