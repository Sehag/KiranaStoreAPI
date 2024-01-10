package com.api.kiranastore.services.transactions;

import com.api.kiranastore.entities.Transactions;
import com.api.kiranastore.entities.Users;
import com.api.kiranastore.enums.Currency;
import com.api.kiranastore.enums.HttpStatus;
import com.api.kiranastore.enums.TransactionType;
import com.api.kiranastore.models.transactions.TransDetails;
import com.api.kiranastore.models.transactions.TransRequest;
import com.api.kiranastore.models.transactions.TransResponse;
import com.api.kiranastore.repo.TransactionsRepo;
import com.api.kiranastore.repo.UsersRepo;
import com.api.kiranastore.response.ApiResponse;
import com.api.kiranastore.security.TokenUtils;
import com.api.kiranastore.services.exchangeRate.ExchangeServices;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TransServicesImpl implements TransService {

    private final TransactionsRepo transactionsRepo;
    private final UsersRepo usersRepo;
    private final ExchangeServices exchangeServices;
    private final TokenUtils tokenUtils;

    public TransServicesImpl(TransactionsRepo transactionsRepo, UsersRepo usersRepo, ExchangeServices exchangeServices, TokenUtils tokenUtils) {
        this.transactionsRepo = transactionsRepo;
        this.usersRepo = usersRepo;
        this.exchangeServices = exchangeServices;
        this.tokenUtils = tokenUtils;
    }

    /**
     * Add the transaction to the database
     * @param token JWT token of the user
     * @param transRequest amount of transaction
     */
    public ApiResponse makePayment(String token, TransRequest transRequest){
        TransResponse transResponse;
        String userId = tokenUtils.extractUserId(token.substring(7));
        Optional<Users> user = usersRepo.findById(userId);
        if(user.isPresent()){
            TransDetails transDetails = new TransDetails();
            Currency currency = user.get().getCurrency();
            String username = user.get().getUsername();
            double amount = transRequest.getAmount();
            double exchangeRate = exchangeServices.getExchangeRateForCurrency(currency);
            double convertedAmount = amount/exchangeRate;
            Transactions savedTrans = transactionsRepo.save(setTransactions(username,convertedAmount,currency));
            transDetails.setTransAmount(amount);
            transDetails.setTransId(savedTrans.getId());
            transDetails.setTransTime(LocalDateTime.now());
            transResponse = new TransResponse(true,transDetails,201,"Transaction successful", HttpStatus.CREATED);
        } else {
            transResponse = new TransResponse(false,null,200,"Transaction failed", HttpStatus.OK);
        }
        return transResponse.getApiResponse();
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
