package com.api.kiranastore.services;

import com.api.kiranastore.entities.Transactions;
import com.api.kiranastore.repo.TransactionsRepo;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public TransactionsRepo transactionsRepo;

    UserService(TransactionsRepo transactionsRepo){
        this.transactionsRepo = transactionsRepo;
    }

    /** Add transactions to database
     * @param trans - amount
     */
    public void addTrans(Transactions trans){
        transactionsRepo.save(trans);
    }
}
