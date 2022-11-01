package com.sparos.uniquone.msauserservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaAuditing
@EnableDiscoveryClient
@EnableAsync
public class MsaUserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsaUserServiceApplication.class, args);
	}

}
