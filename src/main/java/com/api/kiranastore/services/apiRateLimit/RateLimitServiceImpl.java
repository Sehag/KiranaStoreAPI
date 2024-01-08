package com.api.kiranastore.services.apiRateLimit;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.Refill;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.function.Supplier;

@Service
public class RateLimitServiceImpl implements RateLimitService {

    public ProxyManager buckets;

    public Bucket resolveBucket(String role){
        Supplier<BucketConfiguration> configSupplier = getConfigSupplierForUser(role);
        return buckets.builder().build(role,configSupplier);
    }

    private Supplier<BucketConfiguration> getConfigSupplierForUser(String jwtToken) {
        Refill refill = Refill.intervally(10, Duration.ofMinutes(1));
        Bandwidth limit = Bandwidth.classic(20, refill);

        return () -> (BucketConfiguration.builder()
                .addLimit(limit)
                .build());
    }
}
