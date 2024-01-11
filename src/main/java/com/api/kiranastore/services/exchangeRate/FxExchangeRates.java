package com.api.kiranastore.services.exchangeRate;

import com.api.kiranastore.models.exchangeRate.ExchangeRates;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FxExchangeRates {

    @Value("${fxrates.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    public FxExchangeRates(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Fetches the exchange rate for all the currencies using the fxrates Api
     *
     * @return response the fxrate api
     */
    @Cacheable(value = "RedisCache")
    public ExchangeRates getExchangeRates() {
        ExchangeRates exchangeRates = restTemplate.getForObject(apiUrl, ExchangeRates.class);
        return exchangeRates;
    }
}
