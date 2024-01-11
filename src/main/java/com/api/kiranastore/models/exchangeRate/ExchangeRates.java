package com.api.kiranastore.models.exchangeRate;

import com.api.kiranastore.enums.Currency;
import java.util.Map;
import java.util.Set;
import lombok.Data;

@Data
public class ExchangeRates {
    private boolean success;
    private String terms;
    private String privacy;
    private long timestamp;
    private String date;
    private String base;
    private Map<String, Double> rates;

    /**
     * Get exchange rate for the sender's currency
     *
     * @param currency sender's currency
     * @return exchange rate
     */
    public double getExchangeRate(Currency currency) {
        String strCurrency = currency.toString();
        return rates.get(strCurrency);
    }

    /**
     * Retrieve all currencies from the 'rates' map
     *
     * @return All the available currencies
     */
    public Set<String> getAllCurrencies() {
        return rates.keySet();
    }
}
