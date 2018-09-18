package io.github.przbetkier.tuscan.config;

import io.github.przbetkier.tuscan.config.properties.FaceitWebClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.MediaType.*;

@Configuration
class FaceitWebClientConfiguration {

    private final FaceitWebClientProperties faceitWebClientProperties;

    FaceitWebClientConfiguration(FaceitWebClientProperties faceitWebClientProperties) {
        this.faceitWebClientProperties = faceitWebClientProperties;
    }

    @Bean(name = "faceitClient")
    WebClient faceitClient() {
        return WebClient
                .builder()
                .baseUrl(faceitWebClientProperties.getUrl())
                .defaultHeader(ACCEPT, APPLICATION_JSON_VALUE)
                .defaultHeader(AUTHORIZATION, "Bearer " + faceitWebClientProperties.getApiKey())
                .build();
    }

    @Bean(name = "openFaceitClient")
    WebClient openFaceitClient() {
        return WebClient
                .builder()
                .baseUrl("https://api.faceit.com/stats/api/v1")
                .defaultHeader(ACCEPT, APPLICATION_JSON_VALUE)
                .build();
    }
}
