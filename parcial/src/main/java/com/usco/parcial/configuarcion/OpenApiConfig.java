package com.usco.parcial.configuarcion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(new Info()
                .title("API de Consultas Médicas")
                .version("1.0")
                .description("Servicios web para la gestión de consultas médicas por rol"));
    }
}