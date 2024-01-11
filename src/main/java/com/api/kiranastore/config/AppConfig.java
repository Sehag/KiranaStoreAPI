package com.api.kiranastore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    /**
     * Configures and provides a RestTemplate bean.
     *
     * @return instance of RestTemplate class
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
