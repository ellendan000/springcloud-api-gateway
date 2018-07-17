package net.shadow.springcloud.apigateway.configuration;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.google.common.collect.Lists.newArrayList;

@Configuration
@EnableCaching
public class CachingConfiguration {
    public static final String API_AUTHORITIES_CACHE_NAME = "api_authorities";
    public static final String ALL_KEY = "all";

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(newArrayList(
                new ConcurrentMapCache(API_AUTHORITIES_CACHE_NAME)));
        return cacheManager;
    }
}
