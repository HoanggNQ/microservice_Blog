package com.blog.userservice.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class CorsConfig {
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "HEAD", "PATCH"));
        configuration.setAllowedHeaders(Arrays.asList(
                "Accept",
                "Access-Control-Allow-Headers",
                "Access-Control-Allow-Methods",
                "Access-Control-Allow-Origin",
                "Authorization",
                "Content-Type",
                "Origin",
                "X-Requested-With"));
        configuration.setExposedHeaders(Arrays.asList(
                "Accept",
                "Access-Control-Allow-Headers",
                "Access-Control-Allow-Methods",
                "Access-Control-Allow-Origin",
                "Authorization",
                "Content-Type",
                "Origin",
                "X-Requested-With"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
