package com.api.kiranastore.services.transactions;

import com.api.kiranastore.entities.Transactions;
import com.api.kiranastore.entities.Users;
import com.api.kiranastore.enums.Currency;
import com.api.kiranastore.enums.TransactionType;
import com.api.kiranastore.models.transactions.TransactionResponse;
import com.api.kiranastore.repo.TransactionsRepo;
import com.api.kiranastore.repo.UsersRepo;
import com.api.kiranastore.security.TokenUtils;
import com.api.kiranastore.services.exchangeRate.ExchangeServices;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TransactionServices {

    private final TransactionsRepo transactionsRepo;
    private final UsersRepo usersRepo;
    private final ExchangeServices exchangeServices;
    private final TokenUtils tokenUtils;

    public TransactionServices(TransactionsRepo transactionsRepo, UsersRepo usersRepo, ExchangeServices exchangeServices, TokenUtils tokenUtils) {
        this.transactionsRepo = transactionsRepo;
        this.usersRepo = usersRepo;
        this.exchangeServices = exchangeServices;
        this.tokenUtils = tokenUtils;
    }

    /**
     * Add the transaction to the database
     * @param token JWT token of the user
     * @param amount amount of transaction
     */
    public TransactionResponse makePayment(String token, double amount){
        String userId = tokenUtils.extractUserId(token.substring(7));
        TransactionResponse transResponse = new TransactionResponse();
        Optional<Users> user = usersRepo.findById(userId);
        if(user.isPresent()){
            Currency currency = user.get().getCurrency();
            String username = user.get().getUsername();
            double exchangeRate = exchangeServices.getExchangeRateForCurrency(currency);
            double convertedAmount = amount/exchangeRate;
            Transactions savedTrans = transactionsRepo.save(setTransactions(username,convertedAmount,currency));
            transResponse.setTransAmount(amount);
            transResponse.setTransId(savedTrans.getId());
            transResponse.setMessage("Transaction successful");
        } else {
            transResponse.setMessage("Transaction failed");
        }
        return transResponse;
    }

    private Transactions setTransactions(String userName ,double amount,Currency currency){
        Transactions transactions = new Transactions();
        transactions.setAmount(amount);
        transactions.setUserName(userName);
        transactions.setTransTime(LocalDateTime.now());
        transactions.setTransType(TransactionType.CREDIT);
        transactions.setCurrency(currency);
        return transactions;
    }
}
