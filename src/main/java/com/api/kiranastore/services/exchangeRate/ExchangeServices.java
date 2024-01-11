package com.api.kiranastore.services.exchangeRate;

import com.api.kiranastore.enums.Currency;

public interface ExchangeServices {

    public double getExchangeRateForCurrency(Currency currency);
}
