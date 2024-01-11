package com.api.kiranastore.core_auth.services.apiRateLimit;

import io.github.bucket4j.Bucket;

public interface RateLimitService {
    public Bucket resolveBucket(String role);

    public boolean isRateLimitBreached(String role);
}
