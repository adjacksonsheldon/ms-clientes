package com.asps.clientes.core.security;

import com.asps.clientes.core.config.AppConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import java.security.interfaces.RSAPublicKey;

@Configuration
@RequiredArgsConstructor
public class ResourceServerConfig {

    private final AppConfig appConfig;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests()
                .anyRequest().authenticated()
            .and()
                .oauth2ResourceServer().jwt();

        return http.build();
    }

}
