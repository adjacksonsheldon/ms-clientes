package com.asps.clientes.infrastructure.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
public class DbProperties {
    @Value("${username}")
    private String username;

    @Value("${password}")
    private String password;

    @Value("${url}")
    private String url;
}
