package com.asps.clientes.infrastructure.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "db")
public class DbProperties {
    private String url;
    private String user;
    private String password;
}
