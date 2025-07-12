package com.blog.userservice.configs;



import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI customOpenAPI() {

        Server server = new Server();
        server.setUrl("/");
        server.setDescription("Default");

        return new OpenAPI()
                .servers(List.of(server))
                .info(new Info().title("Example API")

                        .version("1.0.0")
                        .description("This is a simple API example using OpenAPI 3.0"))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }


    public OpenApiConfig(MappingJackson2HttpMessageConverter converter) {
        var supportedMediaTypes = new ArrayList<>(converter.getSupportedMediaTypes());
        supportedMediaTypes.add(new MediaType("application", "octet-stream"));
        converter.setSupportedMediaTypes(supportedMediaTypes);
    }
}
