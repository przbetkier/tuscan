package io.github.przbetkier.tuscan.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
@EnableWebFlux
@Profile("prod")
class CorsConfiguration implements WebFluxConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**")
                .allowedOrigins("https://www.tuscan.pro/", "https://tuscan.pro/", "www.tuscan.pro", "tuscan.pro")
                .allowedMethods("GET", "POST")
                .maxAge(3600);
    }
}
