package com.example.art.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
@EnableConfigurationProperties(CacheProperties.class)
public class CacheConfig {

    public static final String COMPROBANTES_CACHE = "comprobantes";

    @Bean
    public CacheManager cacheManager(CacheProperties properties) {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager(COMPROBANTES_CACHE);
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .expireAfterWrite(properties.ttl())
                .maximumSize(properties.maximumSize()));
        return cacheManager;
    }
}
