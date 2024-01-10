package com.api.kiranastore.repo;

import com.api.kiranastore.entities.Transactions;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionsRepo extends MongoRepository<Transactions,String> {

    @Query("{'transTime': {'$gte': ?0, '$lte': ?1}}")
    List<Transactions> findTransactionBetweenDates(LocalDateTime start, LocalDateTime end);
}
