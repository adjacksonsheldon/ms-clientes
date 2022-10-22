package com.asps.clientes.infrastructure.config;

import com.asps.clientes.infrastructure.properties.DbProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
@EntityScan(basePackages = {"com.asps.clientes.domain.model"})
@EnableJpaRepositories(basePackages = {"com.asps.clientes.domain.repository"})
public class DataSourceConfig {

    private final DbProperties dbProperties;

    @Bean("transactionsDataSource")
    @ConfigurationProperties(prefix = "clientes.datasource")
    public DataSource transactionsBatchDataSource(){
        return DataSourceBuilder.create()
                .url(dbProperties.getUrl())
                .username(dbProperties.getUser())
                .password(dbProperties.getPassword())
                .build();
    }
}