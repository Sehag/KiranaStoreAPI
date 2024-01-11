package com.api.kiranastore.services.transactions;

import com.api.kiranastore.models.transactions.TransRequest;
import com.api.kiranastore.response.ApiResponse;

public interface TransService {

    ApiResponse makePayment(String token, TransRequest transRequest);
}
