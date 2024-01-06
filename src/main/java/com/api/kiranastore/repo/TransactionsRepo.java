package com.api.kiranastore.repo;

import com.api.kiranastore.entities.Transactions;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TransactionsRepo extends MongoRepository<Transactions,String> {
}
