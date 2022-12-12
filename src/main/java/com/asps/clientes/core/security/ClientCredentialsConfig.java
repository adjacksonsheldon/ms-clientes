package com.asps.clientes.core.security;

import com.asps.clientes.core.properties.KeyVaultProperties;
import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ClientCredentialsConfig {

    private final KeyVaultProperties keyVaultProperties;

    @Bean
    public ClientSecretCredential getCredentials() {
        ClientSecretCredential secondServicePrincipal = new ClientSecretCredentialBuilder()
                .clientId(keyVaultProperties.getClientId())
                .clientSecret(keyVaultProperties.getClientSecret())
                .tenantId(keyVaultProperties.getTenantId())
                .build();

        return secondServicePrincipal;
    }
}