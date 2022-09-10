package com.teste.desafio.docs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@OpenAPIDefinition(info = @Info(title = "My API", version = "v1"))
public class DocsConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().addServersItem(new io.swagger.v3.oas.models.servers.Server().url("/"));
    }
}

