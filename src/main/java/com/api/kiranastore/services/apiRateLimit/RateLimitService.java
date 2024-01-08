package com.api.kiranastore.services.apiRateLimit;

import io.github.bucket4j.Bucket;

public interface RateLimitService {
    public Bucket resolveBucket(String role);
}
