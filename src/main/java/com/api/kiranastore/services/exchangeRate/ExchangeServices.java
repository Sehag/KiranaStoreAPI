package com.api.kiranastore.services.exchangeRate;

import com.api.kiranastore.models.exchangeRate.ExchangeRates;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExchangeServices {
    private final RestTemplate restTemplate;

    public ExchangeServices(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public double getExchangeRateForCurrency(String countryCode){
        String apiUrl = "https://api.fxratesapi.com/latest?base=INR";
        ExchangeRates exchangeRates = restTemplate.getForObject(apiUrl, ExchangeRates.class);
        if(exchangeRates != null){
            return exchangeRates.getExchangeRate(countryCode);
        } else {
            return 0.0;
        }
    }
}
