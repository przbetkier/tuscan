package io.github.przbetkier.tuscan.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SecurityConfiguration {

    private final String tuscanFrontendUrl;

    public SecurityConfiguration(@Value("${tuscan-frontend-url}") String tuscanFrontendUrl) {
        this.tuscanFrontendUrl = tuscanFrontendUrl;
    }

    @Bean
    @Profile("prod")
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins(tuscanFrontendUrl);
            }
        };
    }

    @Bean
    @Profile("local")
    public WebMvcConfigurer corsConfigurerLocal() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH");
            }
        };
    }
}
