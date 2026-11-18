package com.example.art.config;

import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

@ConfigurationProperties(prefix = "app.materiales-api")
public record MaterialesApiProperties(
        String url,
        @DefaultValue("10s") Duration connectTimeout,
        @DefaultValue("60s") Duration readTimeout
) {
}
