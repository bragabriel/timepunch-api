package io.github.bragabriel.timepunch_api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
		info = @Info(
				title = "Time Punch API",
				description = "API for timing tracking",
				version = "v1.0",
				contact = @Contact(
						name = "Gabriel Braga",
						url = "https://www.linkedin.com/in/gabriel-braga-da-silva/"
				)
		)
)
public class SwaggerConfig {
}
