package com.mtgcollectionorganizer.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.mtgcollectionorganizer.api")
@EnableFeignClients
public class MtgCollectionOrganizerApiModuleApplication {

	public static void main(String[] args) {
		SpringApplication.run(MtgCollectionOrganizerApiModuleApplication.class, args);
	}

}
