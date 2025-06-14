package net.osandman.ai.sandbox.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.ai.gigachat")
public record GigaApiProperties(
    String scope,
    String clientId,
    String clientSecret
) {
}
