package com.ecommerce.ecom.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Allow specific origins (add all possible development URLs)
        config.addAllowedOrigin("http://localhost:5173"); // Vite's default port
        config.addAllowedOrigin("http://localhost:3000");  // React's default port
        config.addAllowedOrigin("http://127.0.0.1:5173");  // Alternative localhost
        config.addAllowedOrigin("http://127.0.0.1:3000");  // Alternative localhost
        config.addAllowedOrigin("https://flipdot.onrender.com");  // Production domain

        // Allow all HTTP methods
        config.addAllowedMethod("*");

        // Allow all headers (including custom headers like Authorization)
        config.addAllowedHeader("*");

        // No longer needed for header-based auth
        config.setAllowCredentials(false);

        // Set max age for preflight cache
        config.setMaxAge(3600L);

        // Allow Authorization header in responses
        config.addExposedHeader("Authorization");

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}