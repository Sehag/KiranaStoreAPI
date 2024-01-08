package com.api.kiranastore.models.exchangeRate;

import lombok.Data;

import java.util.Map;

@Data
public class ExchangeRates {
    private boolean success;
    private String terms;
    private String privacy;
    private long timestamp;
    private String date;
    private String base;
    private Map<String, Double> rates;

    public double getExchangeRate(String countryCode){
        return rates.get(countryCode);
    }
}

