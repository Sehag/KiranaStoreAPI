package com.api.kiranastore.services.transactions;

import com.api.kiranastore.models.transactions.TransRequest;
import com.api.kiranastore.response.ApiResponse;

public interface TransService {

    /**
     * Makes payment
     * @param token Access token of the user
     * @param transRequest amount of transaction
     * @return Status of transaction
     */
    ApiResponse makePayment(String token, TransRequest transRequest);
}
