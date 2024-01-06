package com.api.kiranastore.services;

import com.api.kiranastore.entities.Transactions;
import com.api.kiranastore.repo.TransactionsRepo;
import org.springframework.stereotype.Service;

@Service
public class TransactionServices {

    private final TransactionsRepo transactionsRepo;

    public TransactionServices(TransactionsRepo transactionsRepo) {
        this.transactionsRepo = transactionsRepo;
    }

    /** Add transactions to database
     * @param trans - amount
     */
    public void addTrans(Transactions trans){
        transactionsRepo.save(trans);
    }
}
