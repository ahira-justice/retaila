package com.ahirajustice.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class BeanConfig {
    
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SpringApplicationContext springApplicationContext(){
		return new SpringApplicationContext();
	}

	@Bean
  	public OpenAPI openAPI() {
    	return new OpenAPI()
			.info(new Info().title("Retail API")
			.description("A basic retail application HTTP API")
			.version("v0.0.1"))
			.components(
				new Components()
					.addSecuritySchemes("basic", new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic"))
          			.addSecuritySchemes("bearer", new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT"))
			);
  }
}
