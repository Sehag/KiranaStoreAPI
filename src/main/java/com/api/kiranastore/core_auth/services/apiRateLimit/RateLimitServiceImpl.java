package com.api.kiranastore.core_auth.services.apiRateLimit;

import io.github.bucket4j.*;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import java.time.Duration;
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

    /**
     * Resolves which bucket to be used for limiting based on the role
     *
     * @return bucket
     */
    public Bucket resolveBucket(String role) {
        Supplier<BucketConfiguration> configSupplier = getConfigSupplierForUser();
        return proxyManager.builder().build(role, configSupplier);
    }

    /**
     * Resolves and checks if the bucket is empty for that particular role
     *
     * @param role role of the user
     * @return availability of the bucket
     */
    public boolean isRateLimitBreached(String role) {
        Supplier<BucketConfiguration> configSupplier;
        if (role.equals("USER")) {
            configSupplier = getConfigSupplierForUser();
        } else if (role.equals("ADMIN")) {
            configSupplier = getConfigSupplierForAdmin();
        } else {
            configSupplier = getConfigSupplierForOwner();
        }
        Bucket bucket = proxyManager.builder().build(role, configSupplier);
        return !bucket.tryConsume(1);
    }

    /** TODO : Ask which user should have faster refil of bucket */

    /**
     * Bucket configuration for the role USER
     *
     * @return bucket configuration
     */
    private Supplier<BucketConfiguration> getConfigSupplierForUser() {
        Refill refill = Refill.intervally(1, Duration.ofSeconds(5));
        Bandwidth limit = Bandwidth.classic(4, refill);

        return () -> (BucketConfiguration.builder().addLimit(limit).build());
    }

    /**
     * Bucket configuration for the role ADMIN
     *
     * @return bucket configuration
     */
    private Supplier<BucketConfiguration> getConfigSupplierForAdmin() {
        Refill refill = Refill.intervally(1, Duration.ofSeconds(5));
        Bandwidth limit = Bandwidth.classic(4, refill);

        return () -> (BucketConfiguration.builder().addLimit(limit).build());
    }

    /**
     * Bucket configuration for the role OWNER
     *
     * @return bucket configuration
     */
    private Supplier<BucketConfiguration> getConfigSupplierForOwner() {
        Refill refill = Refill.intervally(1, Duration.ofSeconds(5));
        Bandwidth limit = Bandwidth.classic(4, refill);

        return () -> (BucketConfiguration.builder().addLimit(limit).build());
    }
}
