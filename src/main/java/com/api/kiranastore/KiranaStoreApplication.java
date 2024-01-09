package com.api.kiranastore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class KiranaStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(KiranaStoreApplication.class, args);
    }

}
