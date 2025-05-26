package net.osandman.ai.sandbox.config;

import chat.giga.client.GigaChatClient;
import chat.giga.client.auth.AuthClient;
import chat.giga.client.auth.AuthClientBuilder;
import chat.giga.model.Scope;
import chat.giga.springai.api.auth.GigaChatApiProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GigaChatConfig {

    @Bean
    public GigaChatClient gigaChatClient(
        GigaChatApiProperties properties, @Value("${spring.ai.gigachat.scope}") String scope
    ) {
        return GigaChatClient.builder()
            .verifySslCerts(false)
            .authClient(AuthClient.builder()
                .withOAuth(AuthClientBuilder.OAuthBuilder.builder()
                    .scope(Scope.valueOf(scope.toUpperCase()))
                    .clientId(properties.getClientId())
                    .clientSecret(properties.getClientSecret())
                    .build())
                .build())
            .build();
    }
}
