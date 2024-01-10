package com.api.kiranastore.services.apiRateLimit;

import com.api.kiranastore.enums.Roles;
import io.github.bucket4j.*;
import io.github.bucket4j.distributed.proxy.ProxyManager;

import java.time.Duration;
import java.util.List;
import java.util.function.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RateLimitServiceImpl implements RateLimitService {
    private final ProxyManager<String> proxyManager;

    @Autowired
    RateLimitServiceImpl(ProxyManager<String> proxyManager) {
        this.proxyManager = proxyManager;
    }

    public Bucket resolveBucket(){
        Supplier<BucketConfiguration> configSupplier =  getConfigSupplierForUser();
        return proxyManager.builder().build("",configSupplier);
    }

    private boolean isRateLimitBreached(String role){
        Supplier<BucketConfiguration> configSupplier;
        if (role.equals("USER")){
            configSupplier = getConfigSupplierForUser();
        } else if (role.equals("ADMIN")){
            configSupplier = getConfigSupplierForAdmin();
        } else {
            configSupplier = getConfigSupplierForOwner();
        }
        Bucket bucket = proxyManager.builder().build(role,configSupplier);
        return !bucket.tryConsume(1);
    }

    /**
     *
     * TODO : Ask which user should have faster refil of bucket
     */


    private Supplier<BucketConfiguration> getConfigSupplierForUser() {
        Refill refill = Refill.intervally(1, Duration.ofSeconds(5));
        Bandwidth limit = Bandwidth.classic(4, refill);

        return () -> (BucketConfiguration.builder()
                .addLimit(limit)
                .build());
    }

    private Supplier<BucketConfiguration> getConfigSupplierForAdmin() {
        Refill refill = Refill.intervally(1, Duration.ofSeconds(5));
        Bandwidth limit = Bandwidth.classic(4, refill);

        return () -> (BucketConfiguration.builder()
                .addLimit(limit)
                .build());
    }

    private Supplier<BucketConfiguration> getConfigSupplierForOwner() {
        Refill refill = Refill.intervally(1, Duration.ofSeconds(5));
        Bandwidth limit = Bandwidth.classic(4, refill);

        return () -> (BucketConfiguration.builder()
                .addLimit(limit)
                .build());
    }

}

/*
    public boolean isRateLimitBreached(String key, BucketConfiguration rateLimitConfiguration) {
        Bucket bucket =
                proxyManager
                        .builder()
                        .build(
                                key,
                                getConfigSupplierForBucketConfiguration(
                                        rateLimitConfiguration)); // build(key,
        // rateLimitConfiguration);
        // since the use case is for rate limiting, token consumption will always be one
        return !bucket.tryConsume(1);
    }


    public long getRemainingLimit(String key, BucketConfiguration rateLimitConfiguration) {
        Bucket bucket =
                proxyManager
                        .builder()
                        .build(
                                key,
                                getConfigSupplierForBucketConfiguration(
                                        rateLimitConfiguration)); // build(key,
        // rateLimitConfiguration);
        // Used to get the available limits
        return bucket.getAvailableTokens();
    }


    public void resetLimit(String key, BucketConfiguration rateLimitConfiguration) {
        Bucket bucket =
                proxyManager
                        .builder()
                        .build(
                                key,
                                getConfigSupplierForBucketConfiguration(
                                        rateLimitConfiguration)); // build(key,
        // rateLimitConfiguration);
        // Used to get the available limits
        //bucket.reset();
    }

    private static Supplier<BucketConfiguration> getConfigSupplierForBucketConfiguration(
            BucketConfiguration rateLimitConfiguration) {
        return () -> rateLimitConfiguration;
    }
    */