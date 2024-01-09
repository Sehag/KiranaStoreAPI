package com.api.kiranastore.services.apiRateLimit;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.Refill;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.function.Supplier;

/*
@Configuration
public class RateLimitServiceImpl implements RateLimitService {

    private final ProxyManager<String> proxyManager;

    public RateLimitServiceImpl(ProxyManager<String> proxyManager) {
        this.proxyManager = proxyManager;
    }

    public Bucket resolveBucket(String role){
        Supplier<BucketConfiguration> configSupplier = getConfigSupplierForUser(role);

        // Does not always create a new bucket, but instead returns the existing one if it exists.
        return proxyManager.builder().build(role, configSupplier);
    }

    private Supplier<BucketConfiguration> getConfigSupplierForUser(String role) {
        Refill refill = Refill.intervally(1, Duration.ofSeconds(10));
        Bandwidth limit = Bandwidth.classic(2, refill);

        return () -> (BucketConfiguration.builder()
                .addLimit(limit)
                .build());
    }
}

 */


