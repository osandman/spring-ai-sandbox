package net.osandman.ai.sandbox.config;

import chat.giga.client.GigaChatClient;
import chat.giga.client.auth.AuthClient;
import chat.giga.client.auth.AuthClientBuilder;
import chat.giga.model.Scope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GigaChatConfig {

    @Bean
    public GigaChatClient gigaChatClient(GigaApiProperties properties) {
        return GigaChatClient.builder()
            .verifySslCerts(false)
            .authClient(AuthClient.builder()
                .withOAuth(AuthClientBuilder.OAuthBuilder.builder()
                    .scope(Scope.valueOf(properties.scope().toUpperCase()))
                    .clientId(properties.clientId())
                    .clientSecret(properties.clientSecret())
                    .build())
                .build())
            .build();
    }
}
