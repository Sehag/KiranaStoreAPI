package com.api.kiranastore.repo;

import com.api.kiranastore.entities.Transactions;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface TransactionsRepo extends MongoRepository<Transactions, String> {

    /**
     * Find all the transactions between two dates
     *
     * @param start start date
     * @param end end date
     * @return all the transactions within those two dates
     */
    @Query("{'transTime': {'$gte': ?0, '$lte': ?1}}")
    List<Transactions> findTransactionBetweenDates(LocalDateTime start, LocalDateTime end);
}
