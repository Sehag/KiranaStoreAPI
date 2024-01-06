package com.api.kiranastore.entities;

import com.api.kiranastore.enums.TransactionType;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("transactions")
@Data
public class Transactions {

    @Id private String id;
    private String userName;
    private TransactionType transType;
    private double amount;
    private String currency;
}
