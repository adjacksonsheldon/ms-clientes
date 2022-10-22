package com.asps.clientes;

import com.asps.clientes.infrastructure.properties.DbProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({DbProperties.class})
public class MsClientesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsClientesApplication.class, args);
	}

}
