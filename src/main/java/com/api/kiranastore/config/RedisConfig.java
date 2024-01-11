package com.api.kiranastore.config;

import io.github.bucket4j.distributed.proxy.ProxyManager;
import io.github.bucket4j.grid.jcache.JCacheProxyManager;
import javax.cache.CacheManager;
import javax.cache.Caching;
import org.redisson.config.Config;
import org.redisson.jcache.configuration.RedissonConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableCaching
@Configuration
public class RedisConfig {

    @Value("${redis.url}")
    private String redisUrl;

    /**
     * Configures and provides a Redisson Config bean for connecting to a Redis server.
     *
     * @return a configured instance of Config class for connecting to redis server
     */
    @Bean
    public Config config() {
        Config config = new Config();
        config.useSingleServer().setAddress(redisUrl);
        return config;
    }

    /**
     * Configures and provides a CacheManager bean for caching with Redis using Redisson.
     *
     * @param config The Redisson Config used for configuring the CacheManager.
     * @return A configured instance of the CacheManager for caching with Redis using Redisson.
     */
    @Bean(name = "Mymanager")
    public CacheManager cacheManager(Config config) {
        CacheManager manager = Caching.getCachingProvider().getCacheManager();
        manager.createCache("RedisCache", RedissonConfiguration.fromConfig(config));
        return manager;
    }

    /**
     * Configures and provides a ProxyManager bean for creating a JCacheProxyManager with Redisson.
     *
     * @param cacheManager The CacheManager used for creating the JCacheProxyManager
     * @return A ProxyManager configured to work with the "RedisCache" cache in Redisson.
     */
    @Bean
    ProxyManager<String> proxyManager(CacheManager cacheManager) {
        return new JCacheProxyManager<>(cacheManager.getCache("RedisCache"));
    }
}
