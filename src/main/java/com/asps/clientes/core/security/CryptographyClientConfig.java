package com.asps.clientes.core.security;

import com.asps.clientes.core.properties.KeyVaultProperties;
import com.azure.identity.ClientSecretCredential;
import com.azure.security.keyvault.keys.cryptography.CryptographyClient;
import com.azure.security.keyvault.keys.cryptography.CryptographyClientBuilder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import java.security.interfaces.RSAPublicKey;

@RequiredArgsConstructor
@Configuration
@Getter
public class CryptographyClientConfig {

    private final ClientSecretCredential credentials;
    private final KeyVaultProperties keyVaultProperties;

    @Bean
    public JwtDecoder jwtDecoder() {
        CryptographyClient cryptographyClient = new CryptographyClientBuilder()
                .keyIdentifier(keyVaultProperties.getIdentifier())
                .credential(credentials)
                .buildClient();

        final var publicKey = cryptographyClient.getKey()
                .getKey()
                .toRsa()
                .getPublic();

        return NimbusJwtDecoder
                .withPublicKey((RSAPublicKey) publicKey)
                .build();
    }
}

