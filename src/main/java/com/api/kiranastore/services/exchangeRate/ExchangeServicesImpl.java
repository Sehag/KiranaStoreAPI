package com.api.kiranastore.services.exchangeRate;

import com.api.kiranastore.enums.Currency;
import com.api.kiranastore.enums.HttpStatus;
import com.api.kiranastore.exception.TransException;
import com.api.kiranastore.models.exchangeRate.ExchangeRates;
import org.springframework.stereotype.Service;

@Service
public class ExchangeServicesImpl implements ExchangeServices {
    private final FxExchangeRates fxExchangeRates;

    public ExchangeServicesImpl(FxExchangeRates fxExchangeRates) {
        this.fxExchangeRates = fxExchangeRates;
    }

    /**
     * Fetches the exchange rate for the sender's currency
     *
     * @param currency sender's currency
     * @return exchange rate
     */
    public double getExchangeRateForCurrency(Currency currency) {
        ExchangeRates exchangeRates = fxExchangeRates.getExchangeRates();
        if (exchangeRates != null) {
            return exchangeRates.getExchangeRate(currency);
        } else {
            throw new TransException(
                    false, HttpStatus.BAD_REQUEST, null, "User currency not found", 400);
        }
    }
}
