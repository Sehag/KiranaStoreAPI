package com.api.kiranastore.services.apiRateLimit;

import com.api.kiranastore.enums.Roles;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.BucketConfiguration;

import java.util.List;

public interface RateLimitService {
    public Bucket resolveBucket();
}
