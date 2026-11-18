package com.example.art.config;

import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

@ConfigurationProperties(prefix = "app.cache.comprobantes")
public record CacheProperties(
        @DefaultValue("10m") Duration ttl,
        @DefaultValue("20") long maximumSize
) {
}
