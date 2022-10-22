package com.mtgcollectionorganizer.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.mtgcollectionorganizer.api")
@EnableSwagger2
@EnableFeignClients
public class MtgCollectionOrganizerApiModuleApplication {

	public static void main(String[] args) {
		SpringApplication.run(MtgCollectionOrganizerApiModuleApplication.class, args);
	}

}
