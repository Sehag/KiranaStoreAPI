package com.api.kiranastore.services.exchangeRate;

import com.api.kiranastore.models.exchangeRate.ExchangeRates;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.api.kiranastore.enums.Currency;

import java.util.Set;

@Service
public class ExchangeServices {
    private final RestTemplate restTemplate;

    public ExchangeServices(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public double getExchangeRateForCurrency(Currency currency){
        String apiUrl = "https://api.fxratesapi.com/latest?base=INR";
        ExchangeRates exchangeRates = restTemplate.getForObject(apiUrl, ExchangeRates.class);
        if(exchangeRates != null){
            Set<String> currencies = exchangeRates.getAllCurrencies();
            return exchangeRates.getExchangeRate(currency);
        } else {
            return 0.0;
        }
    }
}
