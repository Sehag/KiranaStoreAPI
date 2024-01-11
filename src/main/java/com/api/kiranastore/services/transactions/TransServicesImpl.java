package com.api.kiranastore.services.transactions;

import com.api.kiranastore.core_auth.security.TokenUtils;
import com.api.kiranastore.entities.Transactions;
import com.api.kiranastore.entities.Users;
import com.api.kiranastore.enums.Currency;
import com.api.kiranastore.enums.HttpStatus;
import com.api.kiranastore.enums.Roles;
import com.api.kiranastore.enums.TransType;
import com.api.kiranastore.exception.TransException;
import com.api.kiranastore.models.transactions.TransDetails;
import com.api.kiranastore.models.transactions.TransRequest;
import com.api.kiranastore.models.transactions.TransResponse;
import com.api.kiranastore.repo.TransactionsRepo;
import com.api.kiranastore.repo.UsersRepo;
import com.api.kiranastore.response.ApiResponse;
import com.api.kiranastore.services.exchangeRate.ExchangeServicesImpl;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class TransServicesImpl implements TransService {

    private final TransactionsRepo transactionsRepo;
    private final UsersRepo usersRepo;
    private final ExchangeServicesImpl exchangeServices;
    private final TokenUtils tokenUtils;

    public TransServicesImpl(
            TransactionsRepo transactionsRepo,
            UsersRepo usersRepo,
            ExchangeServicesImpl exchangeServices,
            TokenUtils tokenUtils) {
        this.transactionsRepo = transactionsRepo;
        this.usersRepo = usersRepo;
        this.exchangeServices = exchangeServices;
        this.tokenUtils = tokenUtils;
    }

    /**
     * Register a transaction
     *
     * @param token Access token of the user
     * @param transRequest amount of transaction
     * @return Status of transaction
     */
    public ApiResponse makePayment(String token, TransRequest transRequest) {
        TransResponse transResponse;
        String userId = tokenUtils.extractUserId(token.substring(7));
        Optional<Users> user = usersRepo.findById(userId);
        try {
            if (user.isPresent()) {
                TransDetails transDetails = new TransDetails();
                Currency currency = user.get().getCurrency();
                String username = user.get().getUsername();
                double amount = transRequest.getAmount();
                List<Roles> role = user.get().getRoles();

                double exchangeRate = exchangeServices.getExchangeRateForCurrency(currency);
                double convertedAmount = amount / exchangeRate;

                Transactions savedTrans =
                        transactionsRepo.save(
                                setTransactions(username, role, convertedAmount, currency));
                transDetails.setTransAmount(amount);
                transDetails.setTransId(savedTrans.getId());
                transDetails.setTransTime(LocalDateTime.now());
                transResponse =
                        new TransResponse(
                                true,
                                transDetails,
                                201,
                                "Transaction successful",
                                HttpStatus.CREATED);
            } else {
                throw new TransException(
                        false, HttpStatus.BAD_REQUEST, null, "User not found", 400);
            }
        } catch (TransException e) {
            return e.getApiResponse();
        }
        return transResponse.getApiResponse();
    }

    /**
     * Builds the Transaction Entity which will be used to update the entry in Mongodb
     *
     * @param userName Sender's username
     * @param roles Sender's role
     * @param amount Amount of transaction
     * @param currency Currency of the inflow
     * @return Transaction entity
     */
    private Transactions setTransactions(
            String userName, List<Roles> roles, double amount, Currency currency) {
        Transactions transactions = new Transactions();
        transactions.setAmount(amount);
        transactions.setUserName(userName);
        transactions.setTransTime(LocalDateTime.now());

        for (Roles role : roles) {
            if (role.equals(Roles.USER)) {
                transactions.setTransType(TransType.CREDIT);
            } else {
                transactions.setTransType(TransType.DEBIT);
            }
        }

        transactions.setCurrency(currency);
        return transactions;
    }
}
