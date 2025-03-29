package com.anshul.helpProject.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
//@EnableOpenApi
public class OpenAPIConfig {
	@Bean
	public OpenAPI requestServiceAPI() {
		return new OpenAPI()
				.info(new Info().title("Help Request Module API")
				.description("This is Rest api for Help Request Module")
				.version("v0.0.1")
				.license(new License().name("ANSHUL")))
				.addSecurityItem(new SecurityRequirement().addList("auth"))
				.components(new io.swagger.v3.oas.models.Components())
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Local Server")
                ));
	}
}