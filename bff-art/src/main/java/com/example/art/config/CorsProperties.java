package com.example.art.config;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

@ConfigurationProperties(prefix = "app.cors")
public record CorsProperties(
        @DefaultValue("http://localhost:4200") List<String> allowedOrigins
) {
}
